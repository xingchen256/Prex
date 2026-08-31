# PrEx MOD Equivalent Exchange Expansion 1.7.10 Port (Minecraft Project Expansion 1.7.10)

中文提供[中文](./src/zh_cn.md)
## Usage

1. **Build**: Place the generated JAR file into the `mods` folder.
2. **Modify the configuration**: Open the `META-INF` folder in the JAR file, locate the corresponding file, and delete the following line:
   `MixinConfigs: mixins.prex.json`
---

## ⚠️ Precautions
- **If you are building the JAR file yourself, please open the abcd file in the libs directory to download the corresponding mod**
- **Total EMC upper limit**: If the EMC exceeds `10^308`, it may lead to issues such as the EMC not displaying properly.
- **Recipe**: If you don't like the new recipe, you can set enableRecipe to false in the prex.cfg file
#### **Possible display anomalies in the Alchemy Chest**:
1. The progress bar and total EMC may display abnormally during the conversion process.
2. When the total EMC of the alchemy chest exceeds `10^40`, the value will no longer increase.
#### **EMC card upper limit issue**:
- If other equivalent exchange extensions are added, items involving EMC exceeding `2^31-1` may lead to a card cap.
- **Recommendation**: Try not to use items with EMC exceeding the upper limit for any purpose other than exchanging them for equivalent original items.
- **Removing this mod after loading will result in exceeding the maximum EMC and resetting it to 0**
### **Compatibility issues**:
- Barring any unexpected issues, except for equivalent auxiliary mods that are compatible with the features, other mods should not be able to load normally or may encounter exceptions. The reasons for this will be mentioned in the implementation plan
#### Provide compatible mods:
- Equivalent Energy Science (please ensure the total EMC of output items does not exceed 10^308, including the GTNH version, and any dependencies of this mod), has implemented all functions of View EMC, PEEX, Tome of Knowledge Sharing, and PEAA
---

## Item and Block Rate Formula

### Power Flowerpot (Output Rate)
- It will provide a certain amount of EMC per second to the player who places it. If the player goes offline, EMC will accumulate. When the player comes back online, the accumulated EMC will be given to the player all at once (if the server is shut down or the chunks are not loaded, the progress will not be saved). Its EMC storage limit is `107,374,182x its own production efficiency`, and it will no longer produce EMC once exceeded
- **mk > 3**：
- 6144 × (4^(mk-4)) × 10 EMC/s
- Recipe: This level of compact energy harvester A, this level of relay B, this level of EMC interface C
- First line: ACA
- Second line: BBB => This level of strength is represented by a flowerpot
- Third line: BBB
### Energy harvester
- Same as the original version
- **mk > 3**：
- **Maximum output efficiency**: `2^(mk+4) × 10` EMC/s
- **Energy storage upper limit**: `2^(mk-3) × 100000`
- Recipe: Upper-level Energy Collector + This Level of Energy => This Level of Collector
### Compact energy harvester
- **For synthesis purposes only, the block itself has no functionality**
- Recipe: 9 x this level of energy collector => this level of compact energy collector
### Relay
- Same as the original version
- **mk > 3**：
- **Output upper limit**: `64 × (mk+1)^2`
- **Energy storage upper limit**: `(mk-2)^2 × 1000_0000`
- Recipe: Superior Relay + This Level of Matter Block => This Level of Relay
### EMC Interface
- **The block function has not been developed yet, and it is currently only used for synthesis purposes**
- Recipe: Low-grade covalent powder A, intermediate covalent powder B, high-grade covalent powder C, substance block of this level D, superior EMC interface E
- First line: ABC
- Second line DED
- Third row: CBA
### Time Position Accelerator
- **The function of the cube is equivalent to an enhanced version of the Time Vortex Pocket Watch, which can accelerate cube operations, plant growth, and random ticks**
- This block has only 6 levels and comes with a significant performance cost, so stacking is not recommended. If you fill a block with ultimate time stance accelerators in a server, a special Easter egg will appear
- It will accelerate the movement of blocks within a range of 16x8x16 (Enderite is 8x4x8) centered around itself, as well as the operation of random ticks
### Power Flowerpot Accelerator
- **Equivalent to a simplified version of a time stance accelerator, it can only accelerate the work of the power flowerpot, but with an extremely high acceleration rate**
- This block also has only 6 levels, but it has minimal performance overhead and is well optimized. However, do not stack it excessively
- It will increase the EMC (Energy Modification Capacity) of the flowerpot, which has a self-centered range of 16x8x16 (where red matter has a range of 8x4x8)
  "Do not attempt to use the Time Standing Device to accelerate the Power Flower Pot Accelerator. The specific principle of this block is explained below."
### Fuel formula
- Superior fuel * 3 + superior fuel block * 1 => this level of fuel
- Superior fuel H, superior fuel block Y
- The first line is HHH
- Second row Y
### Material Recipe
- Superior fuel block H, superior fuel Y, superior substance A, superior substance block B
- The first line is "HYH" followed by "HBH"
- Second row: BAB or YAY
- Third row: HYH - HBH
### Heart of Energy
- Superior Energy Core *4 => This Level of Energy Core
### Ultimate Heart Fragment
- For synthesis purposes only
- Recipe: Level 6 Ultimate Energy Core A, Fading Matter Flower Pot B, Fading Matter Block C, Nether Core D
- The first line is ABA
- Second line CDC
- Third row ABA
### Ultimate Heart
- It can be placed in the center of the conversion table on the right to obtain 200 trillion EMC per time
- You can place it on the left conversion table to empty your EMC
- But there is no migration and replication function
- Recipe: Ultimate Energy Flower Pot A, Ultimate Heart Fragment B, Dragon Egg C
- The first line is ABA
- Second line BCB
- Third row ABA
### Book of Knowledge Sharing
- Shift+right-click to write knowledge, but if the knowledge has already been written, it cannot be written again, and the book will become enchanted
- Right-click to access the knowledge within
- Recipe: Violet Matter Block A, Nether Heart B, Book and Pen C
- The first line is ABA
- Second line BCB
- Third row ABA
### Alchemy Secret Scroll
- There is a recipe to obtain it, but it requires a huge cost, which is approximately 23.84 billion trillion EMC (23,846,627,553,001,305,725,781,076 EMC, or approximately 23.84 × 10^24).
- Recipe: Book of Knowledge Sharing A, Ultimate Heart Fragment B, Ultimate Heart C
- First line: ABA
- Second row BCB
- Third row ABA
---

## New content

- **Comparison: New additions in version 1.12.2:**
- Material and coal blocks for convenient storage.
- Added a higher level of "Energy Star" rating.
- Time Position Accelerator (with high cost) and Strength Flowerpot Accelerator (with friendly cost)
---
- Currently, there is another ported version based on ASM modification caps, which has a more complete porting of item functions,
  https://github.com/YatzCore/FTB-ProjectEX-1.7.10/tree/main/src/main/java/com/latmod/mods/projectex,
  (The following is a bunch of nonsense, ordinary players don't need to read it)
## Implementation Plan (For Developers)

### EMC upper limit processing
- **New player data**: `rEmc` (BigInteger type)
- **New item EMC comparison table**: `rEmcMap` (BigInteger type)
- **Mixin Injection**:
#### Player EMC
- Inject into a large number of classes involving player EMC and item EMC.
- Modified the EMC display and sorting method of the conversion table, as well as the determination of whether items are displayed, which may lead to search bugs. However, it seems that this bug also exists without this mod
- The original version of the player EMC uses `double` to store data, and even if the upper limit is modified, it cannot exceed `10^308`. To avoid losing precision, the original version uses another upper limit
- To avoid loss of accuracy, use `rEmc` to store the actual EMC, and then transmit it back to the original EMC system
#### Item EMC
- When the EMC of an item exceeds `2^31-1` EMC, the rEmcMap registry will be activated to register the EMC of that item
- Use the command `/prex setEmc <EmcValue>` to set the Emc value exceeding the upper limit, and `/prex reload` to register it in rEmcMap
### Recipe calculation
- The mixin will intercept errors where the total EMC of the recipe exceeds the upper limit of `int`.
- Recalculate using the `PrEmcMapV.valueForConversion` function and register it in `rEmcMap`.
- Renran will still register normally in the original EMC registry with the upper limit of the original equivalent exchange system 'int', to avoid errors.
### Block Interception
- Most of the blocks involving EMC have been intercepted and rewritten.
- **The alchemy chest is not yet perfected** (especially for mk2), and some infrequently used blocks have not been intercepted.
- We welcome submissions for improvement.
### Limitations of the scheme
- This approach still has limitations, such as poor compatibility. Therefore, a better solution would be to use ASM to modify bytecode,
  However, the workload should be substantial. Nonetheless, handling the original version in this manner would yield better equivalence and improved compatibility. This is precisely what the new ported version has done,
  And it has implemented many square-like classes and replaced the function of applying energy science
### Power Flowerpot Accelerator
- When the block entity triggers an update and the system timing reaches 990ms
- By scanning the block entities within the acceleration range, determine whether they are power flowerpots, and then increase the ticks of the power flowerpots to achieve acceleration

---

## 🤝 Participate in improvement

If you have a strong desire to improve, feel free to submit a Pull Request.  
Especially for the functions related to the alchemy chest, there is still considerable room for optimization.

---
Translation provided by Baidu AI Translation