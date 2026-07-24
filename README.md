# PrEx MOD等价交换扩展1.7.10移植
# 食用方法
* Build出来后丢Mod文件夹中,然后打开jar的META-INF中的文件,删掉MixinConfigs: mixins.prex.json
一些注意事项 :
总EMC不要超过10^308,建议少使用炼金术箱,特别是emc价值高的,担心吞emc,并且总emc不要大于10^40(即1亿京)不然会直接吞emc
 \
 \
\

关于EMC上限的实现方案:新建玩家数据rEmc,和新EMC与物品对照表rEmcMap,
类型都用BigInter,然后用mixin注入了大量有关于玩家EMC和物品EMC的
class然后再做2^31-1的上限数据给原版物品EMC,但是玩家EMC的double
可以存储巨量的数据所以没回传数据给EMC,配方计算EMC使用mixin拦截了
因超int上限的错误然后使用BigInter重计算,注册到rEmcMap,然后使用
int上限正常返回给原版等价让他以为没问题.