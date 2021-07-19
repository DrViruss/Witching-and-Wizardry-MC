package com.viruss.waw;

import com.viruss.waw.client.RendererManager;
import com.viruss.waw.utils.EventHandler;
import com.viruss.waw.utils.RegistryHandler;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod("waw")
public class WitchingAndWizardry
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "waw";

    public WitchingAndWizardry() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::enqueueIMC);
        bus.addListener(this::processIMC);
        bus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(new EventHandler());
        RegistryHandler.init(bus);
    }

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return RegistryHandler.ASH.getLog().getSecondary().getDefaultInstance();
        }
    };

    public static final RendererManager CLIENT_RENDERER = new RendererManager();
        /*~~~~~~Events~~~~~~*/

    private void doClientStuff(final FMLClientSetupEvent event) {
        CLIENT_RENDERER.init();
}

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
}
