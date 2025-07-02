package com.github.ob_yekt.simplebalance.mixin;

import com.github.ob_yekt.simplebalance.ConfigLoader;
import com.github.ob_yekt.simplebalance.ArmorMaterialIdResolver;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmorMaterial.class)
public class ArmorMaterialMixin {
    @ModifyExpressionValue(
            method = "createAttributeModifiers",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;")
    )
    private Object modifyArmorDefense(Object original, EquipmentType equipmentType) {
        if (!(original instanceof Integer defense)) return original;
        ArmorMaterial material = (ArmorMaterial) (Object) this;

        String id = ArmorMaterialIdResolver.getId(material);
        int customValue = ConfigLoader.getDefense(id, equipmentType);

        return customValue != -1 ? customValue : defense;
    }
}
