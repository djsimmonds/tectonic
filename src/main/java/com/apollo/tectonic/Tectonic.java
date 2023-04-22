package com.apollo.tectonic;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.resource.PathResourcePack;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;
import java.io.IOException;

@Mod(Tectonic.MOD_ID)
public class Tectonic {

	public static final String MOD_ID = "tectonic";

	public Tectonic() {
		
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(Type.COMMON, TectonicConfig.GENERAL_SPEC, "tectonicconfig.toml");		
		
		modEventBus.addListener(this::setupBuiltInDatapack);
		MinecraftForge.EVENT_BUS.register(this);
		
        RegisterTectonicBiomes.REGISTER.register(modEventBus);
	}    

	private void setupBuiltInDatapack(AddPackFindersEvent event) {	

		String MODID = "tectonic";
		
		if (event.getPackType() == PackType.SERVER_DATA) {
			if ((Boolean)TectonicConfig.LARGE_BIOMES_ENABLED.get()) {
				var resourcePath = ModList.get().getModFileById(MODID).getFile().findResource("resourcepacks/tectonic-large-biomes");

	            try (var pack = new PathResourcePack(ModList.get().getModFileById(MODID).getFile().getFileName() + ":" + resourcePath, resourcePath)) {
					var metadataSection = pack.getMetadataSection(PackMetadataSection.SERIALIZER);
					if (metadataSection != null)
					{
					    event.addRepositorySource((packConsumer, packConstructor) ->
					            packConsumer.accept(packConstructor.create(
					            		"builtin/tectonic-large-biomes", new TextComponent("Tectonic: Large Biomes"), false,
					                    () -> pack, metadataSection, Pack.Position.TOP, PackSource.BUILT_IN, false)));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			String MODDATAPACK = ((ModList.get().isLoaded("terralith")) ? "terratonic" : "tectonic");
				
            var resourcePath = ModList.get().getModFileById(MODID).getFile().findResource("resourcepacks/" + MODDATAPACK);

            try (var pack = new PathResourcePack(ModList.get().getModFileById(MODID).getFile().getFileName() + ":" + resourcePath, resourcePath)) {
				var metadataSection = pack.getMetadataSection(PackMetadataSection.SERIALIZER);
				if (metadataSection != null)
				{
				    event.addRepositorySource((packConsumer, packConstructor) ->
				            packConsumer.accept(packConstructor.create(
				            		"builtin/" + MODDATAPACK, new TextComponent("Tectonic: " + MODDATAPACK), false,
				                    () -> pack, metadataSection, Pack.Position.TOP, PackSource.BUILT_IN, false)));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}    
	}	
}
