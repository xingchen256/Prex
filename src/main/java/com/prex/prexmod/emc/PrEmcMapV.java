package com.prex.prexmod.emc;

import moze_intel.projecte.emc.NormalizedSimpleStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Map;

public class PrEmcMapV {
    public static BigInteger valueForConversion(
            Map<?, ?> values,
            Object conversion
    ) {

        try {

            // conversion.value
            Field valueField = conversion.getClass().getDeclaredField("value");
            valueField.setAccessible(true);

            Object baseValue = valueField.get(conversion);

            BigInteger value = toBigInteger(baseValue);


            //获取conversion.ingredientsWithAmount
            Field ingredientField =
                    conversion.getClass().getDeclaredField("ingredientsWithAmount");

            ingredientField.setAccessible(true);

            Map<?, Integer> ingredients =
                    (Map<?, Integer>) ingredientField.get(conversion);


            boolean allIngredientsAreFree = true;
            boolean hasPositiveIngredientValues = false;


            for (Map.Entry<?, Integer> entry : ingredients.entrySet()) {


                Object ingredient = entry.getKey();
                int amount = entry.getValue();

                NormalizedSimpleStack.NSSItem item = (NormalizedSimpleStack.NSSItem) ingredient;//ItemStack获取
                ItemStack IsItem=new ItemStack((Item)Item.itemRegistry.getObject(item.itemName),1,item.damage);
                if (!values.containsKey(ingredient) && !PrEmcMap.contains(IsItem)) {//获取是否有EMC
                    return BigInteger.ZERO;
                }


                if (amount == 0) {
                    continue;
                }


                Object ingredientRaw = values.get(ingredient);
                BigInteger ingredientValue =
                        toBigInteger(ingredientRaw)
                                .multiply(BigInteger.valueOf(amount));

                if(PrEmcMap.contains(IsItem)){ingredientValue=PrEmcMap.get(IsItem).multiply(BigInteger.valueOf(amount));}//PrEmcMap获取

                if (ingredientValue.compareTo(BigInteger.ZERO) == 0) {
//                    FMLLog.info(String.format("%s: %s",IsItem,ingredientValue));
//                    FMLLog.info("amount"+amount);
//                    FMLLog.info("QWWWSS");
                    return BigInteger.ZERO;
                }


                // isFree
                if (!ingredientValue.equals(BigInteger.ZERO)) {
                    value = value.add(ingredientValue);
                    if (ingredientValue.compareTo(BigInteger.ZERO) > 0
                            && amount > 0) {

                        hasPositiveIngredientValues = true;
                    }


                    allIngredientsAreFree = false;
                }
            }


            /*
             * 对应原版:
             *
             * if(allIngredientsAreFree ||
             *    (hasPositiveIngredientValues && value <= 0))
             *      return free
             */

            if (allIngredientsAreFree
                    || (hasPositiveIngredientValues
                    && value.compareTo(BigInteger.ZERO)<=0)) {
                return BigInteger.ZERO;
            }


            return value;


        } catch (Exception e) {

            e.printStackTrace();
            return BigInteger.ZERO;
        }
    }



    private static BigInteger toBigInteger(Object obj) {


        if (obj == null) {
            return BigInteger.ZERO;
        }


        if (obj instanceof BigInteger) {
            return (BigInteger)obj;
        }


        if (obj instanceof Number) {

            return BigInteger.valueOf(
                    ((Number)obj).longValue()
            );
        }


        return new BigInteger(obj.toString());
    }
}
