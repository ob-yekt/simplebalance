# Changelog

---

## ⚙️ Features

### 🏋️ Ironman Mode
- Players can enable Ironman Mode by using the command `/ironman enable`
    - **All items and levels are lost upon death in Ironman Mode**
    - **Health is reduced by 50%.**
    - **Ironman Mode players are marked on the leaderboard with a red skull.**

### 🧠 Difficulty Tweaks
- Local Difficulty is always set to its maximum value based on the current difficulty setting:
    - **Peaceful:** 0.0
    - **Easy:** 1.5
    - **Normal:** 4.0
    - **Hard:** 6.75
- Added a `/localdifficulty` command to check the current local difficulty value.
- Fully server-side.

---

## 🛡️ Armor & Crafting Overhaul
- All armor values have been rebalanced. Values are **_configurable via a generated JSON file_** on first run.
    - **Default Full Set Defense:**
        - Leather: 6
        - Chainmail: 8
        - Golden: 10
        - Iron: 14
        - Diamond: 18
        - Netherite: 20
- Chainmail is now craftable using the old iron recipe (with iron ingots).
- Crafting Changes:
    - Iron & Diamond Armor: Require blocks instead of ingots or single diamonds.
    - Netherite Armor: Upgraded using a netherite **block** instead of an ingot.
    - Diamond Tools: Require **diamond blocks** to craft.
    - Shields: Crafted with iron ingots plus one iron block.

---

## 📦 Loot & Trade Adjustments

### Loot Tables
- Iron and diamond armor/tools no longer appear in chest loot.
- Mobs no longer drop their equipped armor.

### Villager Trades

#### Librarians
- No longer offer treasure enchantments (e.g., Mending).
- Enchanted book trades only require emeralds, no books.
- Can offer enchantments up to **Max Level - 1** (unless the max level is 1).
    - Example: Protection max level 4 → only up to level 3 offered.
    - Max level 1 enchants like Silk Touch are still available.

#### General Gear
- Iron and diamond gear are no longer available through villager trading.

### Piglins
- May barter chainmail boots with Soul Speed instead of iron boots.

---

## 📦 General Trade Changes
- ❌ **Removed:**
    - All diamond tools, diamond armor, and iron armor from villager trades.

---

## 🛠️ Toolsmith

### Level 4 (Expert)
- ✅ Added: `1 Diamond → 1 Emerald` (Max uses: 12)
- ✅ Added: `4 Emeralds → 1 Brush` (Max uses: 3)
- ✅ Added: `1 Emerald → 2 Honeycomb` (Max uses: 12)

### Level 5 (Master)
- ✅ Added: `24–32 Emeralds → 1 Recovery Compass` (Max uses: 3)
- ✅ Added: `8 Emeralds → 1 Spyglass` (Max uses: 3)

---

## ⚔️ Weaponsmith

### Level 4 (Expert)
- ✅ Added: `1 Emerald → 2 Gunpowder` (Max uses: 12)

### Level 5 (Master)
- ✅ Added: `48–64 Emeralds → 1 Trident` (Max uses: 3)
- ✅ Added: `4 Emeralds → 1 TNT` (Max uses: 3)

---

## 🛡️ Armorer

### Level 1 (Novice)
- ✅ Added: `12 Raw Copper → 1 Emerald` (Max uses: 16)

### Level 4 (Expert)
- ✅ Added: `24 Emeralds → 1 Anvil` (Max uses: 3)
- ✅ Added: `12 Emeralds → 1 Golden Horse Armor` (Max uses: 3)

### Level 5 (Master)
- ✅ Added: `16–24 Emeralds → 1 Wolf Armor` (Max uses: 3)

---

## 📚 Librarian

### Levels 1–4 (Novice to Expert)
- ✅ Enchanted books require emeralds only, no empty books.
- ✅ All enchantments offered by Librarians are capped at **Max Level - 1**, except when max level is 1 
  - (e.g., Silk Touch is allowed, Protection max is 3, Unbreaking max is 2, Efficiency max is 4 etc.).
- ❌ Removed all treasure enchantments except curses, including:
    - Mending
    - Frost Walker
    - Soul Speed
    - Swift Sneak
    - Wind Burst
---
