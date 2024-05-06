package net.pmkjun.pyrofishinghelper.fabric.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import net.pmkjun.pyrofishinghelper.item.FishItemList;

import java.util.Arrays;

public class FishItems {
    public static final Item[] COMMON_FISH = new Item[FishItemList.COMMMON_FISH_LIST.length];
    public static final Item[] UNCOMMON_FISH = new Item[FishItemList.UNCOMMON_FISH_LIST.length];
    public static final Item[] RARE_FISH = new Item[FishItemList.RARE_FISH_LIST.length];
    public static final Item[] EPIC_FISH = new Item[FishItemList.EPIC_FISH_LIST.length];
    public static final Item[] LEGENDARY_FISH = new Item[FishItemList.LEGENDARY_FISH_LIST.length];
    public static final Item[] MYTHIC_FISH = new Item[FishItemList.MYTHIC_FISH_LIST.length];
    public static void register(){
        int i;
        for(i = 0; i<COMMON_FISH.length; i++){
            COMMON_FISH[i] = new Item(new Item.Settings());
            Registry.register(Registries.ITEM, new Identifier("pyrofishinghelper", "common_"+i), COMMON_FISH[i]);
        }
        for(i = 0; i<UNCOMMON_FISH.length; i++){
            UNCOMMON_FISH[i] = new Item(new Item.Settings());
            Registry.register(Registries.ITEM, new Identifier("pyrofishinghelper", "uncommon_"+i), UNCOMMON_FISH[i]);
        }
        for(i = 0; i<RARE_FISH.length; i++){
            RARE_FISH[i] = new Item(new Item.Settings());
            Registry.register(Registries.ITEM, new Identifier("pyrofishinghelper", "rare_"+i), RARE_FISH[i]);
        }
        for(i = 0; i<EPIC_FISH.length; i++){
            EPIC_FISH[i] = new Item(new Item.Settings());
            Registry.register(Registries.ITEM, new Identifier("pyrofishinghelper", "epic_"+i), EPIC_FISH[i]);
        }
        for(i = 0; i<LEGENDARY_FISH.length; i++){
            LEGENDARY_FISH[i] = new Item(new Item.Settings());
            Registry.register(Registries.ITEM, new Identifier("pyrofishinghelper", "legendary_"+i), LEGENDARY_FISH[i]);
        }
        for(i = 0; i<MYTHIC_FISH.length; i++){
            MYTHIC_FISH[i] = new Item(new Item.Settings());
            Registry.register(Registries.ITEM, new Identifier("pyrofishinghelper", "mythic_"+i), MYTHIC_FISH[i]);
        }

    }
    public static Item getFishItem(ItemStack itemStack){
        String name = itemStack.getName().getString();
        int index;

        if(!(itemStack.getItem().toString().equals("cod"))) return null;
        if(!FishHelperClient.getInstance().data.toggleCustomTexture) return null;

        index = Arrays.stream(FishItemList.COMMMON_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return COMMON_FISH[index];

        index = Arrays.stream(FishItemList.UNCOMMON_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return UNCOMMON_FISH[index];

        index = Arrays.stream(FishItemList.RARE_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return RARE_FISH[index];

        index = Arrays.stream(FishItemList.EPIC_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return EPIC_FISH[index];

        index = Arrays.stream(FishItemList.LEGENDARY_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return LEGENDARY_FISH[index];

        index = Arrays.stream(FishItemList.MYTHIC_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return MYTHIC_FISH[index];

        return null;
    }
}