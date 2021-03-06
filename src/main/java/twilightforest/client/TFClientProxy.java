package twilightforest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import twilightforest.TFCommonProxy;
import twilightforest.TwilightForestMod;
import twilightforest.block.ColorHandler;
import twilightforest.client.model.*;
import twilightforest.client.particle.*;
import twilightforest.client.renderer.TileEntityTFCicadaRenderer;
import twilightforest.client.renderer.TileEntityTFFireflyRenderer;
import twilightforest.client.renderer.TileEntityTFMoonwormRenderer;
import twilightforest.client.renderer.TileEntityTFTrophyRenderer;
import twilightforest.client.renderer.entity.*;
import twilightforest.entity.*;
import twilightforest.entity.boss.*;
import twilightforest.entity.passive.EntityTFBighorn;
import twilightforest.entity.passive.EntityTFBoar;
import twilightforest.entity.passive.EntityTFBunny;
import twilightforest.entity.passive.EntityTFDeer;
import twilightforest.entity.passive.EntityTFMobileFirefly;
import twilightforest.entity.passive.EntityTFPenguin;
import twilightforest.entity.passive.EntityTFQuestRam;
import twilightforest.entity.passive.EntityTFRaven;
import twilightforest.entity.passive.EntityTFSquirrel;
import twilightforest.entity.passive.EntityTFTinyBird;
import twilightforest.entity.passive.EntityTFTinyFirefly;
import twilightforest.item.TFItems;
import twilightforest.tileentity.TileEntityTFCicada;
import twilightforest.tileentity.TileEntityTFFirefly;
import twilightforest.tileentity.TileEntityTFMoonworm;
import twilightforest.tileentity.TileEntityTFTrophy;

import java.util.EnumMap;
import java.util.Map;

public class TFClientProxy extends TFCommonProxy {
	private final Map<EntityEquipmentSlot, ModelBiped> knightlyArmorModel = new EnumMap<>(EntityEquipmentSlot.class);
	private final Map<EntityEquipmentSlot, ModelBiped> phantomArmorModel = new EnumMap<>(EntityEquipmentSlot.class);
	private final Map<EntityEquipmentSlot, ModelBiped> yetiArmorModel = new EnumMap<>(EntityEquipmentSlot.class);
	private final Map<EntityEquipmentSlot, ModelBiped> arcticArmorModel = new EnumMap<>(EntityEquipmentSlot.class);
	private final Map<EntityEquipmentSlot, ModelBiped> fieryArmorModel = new EnumMap<>(EntityEquipmentSlot.class);

	private boolean isDangerOverlayShown;

	@Override
	public void doPreLoadRegistration() {
		RenderingRegistry.registerEntityRenderingHandler(EntityTFBoar.class, m -> new RenderTFBoar(m, new ModelTFBoar()));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFBighorn.class, m -> new RenderTFBighorn(m, new ModelTFBighorn(), new ModelTFBighornFur(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFDeer.class, m -> new RenderTFDeer(m, new ModelTFDeer(), 0.7F));

		RenderingRegistry.registerEntityRenderingHandler(EntityTFRedcap.class, m -> new RenderTFBiped<>(m, new ModelTFRedcap(), 0.625F, "redcap.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFTinyFirefly.class, RenderTFTinyFirefly::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFSkeletonDruid.class, m -> new RenderTFBiped<>(m, new ModelTFSkeletonDruid(), 0.5F, "skeletondruid.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFWraith.class, m -> new RenderTFWraith(m, new ModelTFWraith(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFHydra.class, m -> new RenderTFHydra(m, new ModelTFHydra(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFLich.class, m -> new RenderTFLich(m, new ModelTFLich(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFPenguin.class, m -> new RenderTFBird(m, new ModelTFPenguin(), 1.0F, "penguin.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFLichMinion.class, m -> new RenderTFBiped<>(m, new ModelTFLichMinion(), 1.0F, "textures/entity/zombie/zombie.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFLoyalZombie.class, m -> new RenderTFBiped<>(m, new ModelTFLoyalZombie(), 1.0F, "textures/entity/zombie/zombie.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFTinyBird.class, m -> new RenderTFTinyBird(m, new ModelTFTinyBird(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFSquirrel.class, m -> new RenderTFGenericLiving<>(m, new ModelTFSquirrel(), 1.0F, "squirrel2.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFBunny.class, m -> new RenderTFBunny(m, new ModelTFBunny(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFRaven.class, m -> new RenderTFBird(m, new ModelTFRaven(), 1.0F, "raven.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFQuestRam.class, RenderTFQuestRam::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFKobold.class, m -> new RenderTFKobold(m, new ModelTFKobold(), 0.625F, "kobold.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFBoggard.class, m -> new RenderTFBiped<>(m, new ModelTFLoyalZombie(), 0.625F, "kobold.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFMosquitoSwarm.class, m -> new RenderTFGenericLiving<>(m, new ModelTFMosquitoSwarm(), 0.625F, "mosquitoswarm.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFDeathTome.class, m -> new RenderTFGenericLiving<>(m, new ModelTFDeathTome(), 0.625F, "textures/entity/enchanting_table_book.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFMinotaur.class, m -> new RenderTFBiped<>(m, new ModelTFMinotaur(), 0.625F, "minotaur.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFMinoshroom.class, m -> new RenderTFMinoshroom(m, new ModelTFMinoshroom(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFFireBeetle.class, m -> new RenderTFGenericLiving<>(m, new ModelTFFireBeetle(), 1.1F, "firebeetle.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFSlimeBeetle.class, m -> new RenderTFSlimeBeetle(m, new ModelTFSlimeBeetle(), 1.1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFPinchBeetle.class, m -> new RenderTFGenericLiving<>(m, new ModelTFPinchBeetle(), 1.1F, "pinchbeetle.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFMistWolf.class, RenderTFMistWolf::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFMobileFirefly.class, RenderTFTinyFirefly::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFMiniGhast.class, m -> new RenderTFGhast(m, new ModelTFGhast(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFTowerGolem.class, m -> new RenderTFTowerGolem(m, new ModelTFTowerGolem(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFTowerTermite.class, m -> new RenderTFGenericLiving<>(m, new ModelSilverfish(), 0.3F, "towertermite.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFTowerGhast.class, m -> new RenderTFTowerGhast(m, new ModelTFGhast(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFUrGhast.class, m -> new RenderTFUrGhast(m, new ModelTFTowerBoss(), 0.625F, 24F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFBlockGoblin.class, m -> new RenderTFBlockGoblin(m, new ModelTFBlockGoblin(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFGoblinChain.class, m -> new RenderTFSpikeBlock(m, new ModelTFGoblinChain()));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFSpikeBlock.class, m -> new RenderTFSpikeBlock(m, new ModelTFSpikeBlock()));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFGoblinKnightUpper.class, m -> new RenderTFGoblinKnightUpper(m, new ModelTFGoblinKnightUpper(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFGoblinKnightLower.class, m -> new RenderTFBiped<>(m, new ModelTFGoblinKnightLower(), 0.625F, "doublegoblin.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFHelmetCrab.class, m -> new RenderTFGenericLiving<>(m, new ModelTFHelmetCrab(), 0.625F, "helmetcrab.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFKnightPhantom.class, m -> new RenderTFKnightPhantom(m, new ModelTFKnightPhantom2(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFNaga.class, m -> new RenderTFNaga(m, new ModelTFNaga(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFNagaSegment.class, m -> new RenderTFNagaSegment(m, new ModelTFNaga()));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFSwarmSpider.class, RenderTFSwarmSpider::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFKingSpider.class, RenderTFKingSpider::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFTowerBroodling.class, RenderTFTowerBroodling::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFHedgeSpider.class, RenderTFHedgeSpider::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFRedcapSapper.class, m -> new RenderTFBiped<>(m, new ModelTFRedcap(), 0.625F, "redcapsapper.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFMazeSlime.class, m -> new RenderTFMazeSlime(m, 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFYeti.class, m -> new RenderTFYeti(m, new ModelTFYeti(), 0.625F, "yeti2.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFProtectionBox.class, RenderTFProtectionBox::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFYetiAlpha.class, m -> new RenderTFYeti(m, new ModelTFYetiAlpha(), 0.625F, "yetialpha.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFWinterWolf.class, RenderTFWinterWolf::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFSnowGuardian.class, RenderTFSnowGuardian::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFIceShooter.class, RenderTFIceShooter::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFIceExploder.class, RenderTFIceExploder::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFSnowQueen.class, RenderTFSnowQueen::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFSnowQueenIceShield.class, RenderTFSnowQueenIceShield::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFTroll.class, m -> new RenderTFBiped<>(m, new ModelTFTroll(), 0.625F, "troll.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFGiantMiner.class, RenderTFGiant::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFIceCrystal.class, RenderTFIceCrystal::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFChainBlock.class, m -> new RenderTFChainBlock(m, new ModelTFSpikeBlock()));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFCubeOfAnnihilation.class, RenderTFCubeOfAnnihilation::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFHarbingerCube.class, RenderTFHarbingerCube::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFAdherent.class, m -> new RenderTFAdherent(m, new ModelTFAdherent(), 0.625F, "adherent.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFRovingCube.class, RenderTFRovingCube::new);

		// projectiles
		RenderingRegistry.registerEntityRenderingHandler(EntityTFNatureBolt.class, m -> new RenderSnowball<>(m, Items.WHEAT_SEEDS, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFLichBolt.class, m -> new RenderSnowball<>(m, Items.ENDER_PEARL, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFTwilightWandBolt.class, m -> new RenderSnowball<>(m, Items.ENDER_PEARL, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFTomeBolt.class, m -> new RenderSnowball<>(m, Items.PAPER, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFHydraMortar.class, RenderTFHydraMortar::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFSlimeProjectile.class, m -> new RenderSnowball<>(m, Items.SLIME_BALL, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFMoonwormShot.class, RenderTFMoonwormShot::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFCharmEffect.class, m -> new RenderTFCharm(m, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFLichBomb.class, m -> new RenderSnowball<>(m, Items.MAGMA_CREAM, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFThrownAxe.class, m -> new RenderTFThrownAxe(m, TFItems.knightlyAxe));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFThrownPick.class, m -> new RenderTFThrownAxe(m, TFItems.knightlyPick));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFFallingIce.class, RenderTFFallingIce::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFIceBomb.class, RenderTFThrownIce::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTFIceSnowball.class, m -> new RenderSnowball<>(m, Items.SNOWBALL, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFSlideBlock.class, RenderTFSlideBlock::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySeekerArrow.class, RenderDefaultArrow::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityIceArrow.class, RenderDefaultArrow::new);

		// I guess the hydra gets its own section
		RenderingRegistry.registerEntityRenderingHandler(EntityTFHydraHead.class, m -> new RenderTFHydraHead(m, new ModelTFHydraHead(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTFHydraNeck.class, m -> new RenderTFGenericLiving<>(m, new ModelTFHydraNeck(), 1.0F, "hydra4.png"));
	}

	@Override
	public void doOnLoadRegistration() {
		ColorHandler.init();

		// tile entities
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFFirefly.class, new TileEntityTFFireflyRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFCicada.class, new TileEntityTFCicadaRenderer());
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFNagaSpawner.class, new TileEntityMobSpawnerRenderer());
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFLichSpawner.class, new TileEntityMobSpawnerRenderer());
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFHydraSpawner.class, new TileEntityMobSpawnerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFMoonworm.class, new TileEntityTFMoonwormRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFTrophy.class, new TileEntityTFTrophyRenderer());

//FIXME: AtomicBlom: These all need to be rewritten from scratch.
/*
		// map item renderer
		MinecraftForgeClient.registerItemRenderer(TFItems.magicMap, new TFMagicMapRenderer(mc.gameSettings, mc.getTextureManager()));
		TFMazeMapRenderer mazeRenderer = new TFMazeMapRenderer(mc.gameSettings, mc.getTextureManager());
		MinecraftForgeClient.registerItemRenderer(TFItems.mazeMap, mazeRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.oreMap, mazeRenderer);
		
		TFGiantBlockRenderer giantBlockRenderer = new TFGiantBlockRenderer(mc.gameSettings, mc.getTextureManager());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TFBlocks.giantLeaves), giantBlockRenderer);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TFBlocks.giantCobble), giantBlockRenderer);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TFBlocks.giantLog), giantBlockRenderer);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TFBlocks.giantObsidian), giantBlockRenderer);

		// fiery item render
		TFFieryItemRenderer fieryRenderer = new TFFieryItemRenderer(mc.gameSettings, mc.getTextureManager());
		MinecraftForgeClient.registerItemRenderer(TFItems.fieryPick, fieryRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.fierySword, fieryRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.fieryIngot, fieryRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.fieryHelm, fieryRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.fieryPlate, fieryRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.fieryLegs, fieryRenderer);
		MinecraftForgeClient.registerItemRenderer(TFItems.fieryBoots, fieryRenderer);
*/

		// block render ids
//FIXME: AtomicBlom: These all need BlockState models.
/*		RenderingRegistry.registerBlockHandler(new RenderBlockTFFireflyJar(blockComplexRenderID));
		RenderingRegistry.registerBlockHandler(new RenderBlockTFPlants(plantRenderID));
		RenderingRegistry.registerBlockHandler(new RenderBlockTFCritters(critterRenderID));
		RenderingRegistry.registerBlockHandler(new RenderBlockTFNagastone(nagastoneRenderID));
		RenderingRegistry.registerBlockHandler(new RenderBlockTFMagicLeaves(magicLeavesRenderID));
		RenderingRegistry.registerBlockHandler(new RenderBlockTFPedestal(pedestalRenderID));
		RenderingRegistry.registerBlockHandler(new RenderBlockTFThorns(thornsRenderID));
		RenderingRegistry.registerBlockHandler(new RenderBlockTFKnightMetal(knightmetalBlockRenderID));
		RenderingRegistry.registerBlockHandler(new RenderBlockTFHugeLilyPad(hugeLilyPadBlockRenderID));
		RenderingRegistry.registerBlockHandler(new RenderBlockTFCastleMagic(castleMagicBlockRenderID));
*/
		knightlyArmorModel.put(EntityEquipmentSlot.HEAD, new ModelTFKnightlyArmor(0.5F));
		knightlyArmorModel.put(EntityEquipmentSlot.CHEST, new ModelTFKnightlyArmor(1.0F));
		knightlyArmorModel.put(EntityEquipmentSlot.LEGS, new ModelTFKnightlyArmor(0.5F));
		knightlyArmorModel.put(EntityEquipmentSlot.FEET, new ModelTFKnightlyArmor(0.5F));

		phantomArmorModel.put(EntityEquipmentSlot.HEAD, new ModelTFPhantomArmor(EntityEquipmentSlot.HEAD, 0.5F));
		phantomArmorModel.put(EntityEquipmentSlot.CHEST, new ModelTFPhantomArmor(EntityEquipmentSlot.CHEST, 0.5F));

		yetiArmorModel.put(EntityEquipmentSlot.HEAD, new ModelTFYetiArmor(EntityEquipmentSlot.HEAD, 0.6F));
		yetiArmorModel.put(EntityEquipmentSlot.CHEST, new ModelTFYetiArmor(EntityEquipmentSlot.CHEST, 1.0F));
		yetiArmorModel.put(EntityEquipmentSlot.LEGS, new ModelTFYetiArmor(EntityEquipmentSlot.LEGS, 0.4F));
		yetiArmorModel.put(EntityEquipmentSlot.FEET, new ModelTFYetiArmor(EntityEquipmentSlot.FEET, 0.55F));

		arcticArmorModel.put(EntityEquipmentSlot.HEAD, new ModelTFArcticArmor(0.6F));
		arcticArmorModel.put(EntityEquipmentSlot.CHEST, new ModelTFArcticArmor(1.0F));
		arcticArmorModel.put(EntityEquipmentSlot.LEGS, new ModelTFArcticArmor(0.4F));
		arcticArmorModel.put(EntityEquipmentSlot.FEET, new ModelTFArcticArmor(0.55F));

		fieryArmorModel.put(EntityEquipmentSlot.HEAD, new ModelTFFieryArmor(0.5F));
		fieryArmorModel.put(EntityEquipmentSlot.CHEST, new ModelTFFieryArmor(1.0F));
		fieryArmorModel.put(EntityEquipmentSlot.LEGS, new ModelTFFieryArmor(0.5F));
		fieryArmorModel.put(EntityEquipmentSlot.FEET, new ModelTFFieryArmor(0.5F));
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().world;
	}


	// [VanillaCopy] adapted from RenderGlobal.spawnEntityFX
	@Override
	public void spawnParticle(World world, TFParticleType particleType, double x, double y, double z, double velX, double velY, double velZ) {
		Minecraft mc = Minecraft.getMinecraft();
		Entity entity = mc.getRenderViewEntity();

		// ignore the passed-in world, since on SP we get the integrated server world, which is not really what we want
		world = this.getClientWorld();

		if (entity != null && mc.effectRenderer != null) {
			int i = mc.gameSettings.particleSetting;

			if (i == 1 && world.rand.nextInt(3) == 0) {
				i = 2;
			}

			double d0 = entity.posX - x;
			double d1 = entity.posY - y;
			double d2 = entity.posZ - z;

			if (d0 * d0 + d1 * d1 + d2 * d2 <= 1024D && i <= 1) {
				Particle particle = null;

				switch (particleType) {
					case LARGE_FLAME:
						particle = new ParticleLargeFlame(world, x, y, z, velX, velY, velZ);
						break;
					case LEAF_RUNE:
						particle = new ParticleLeafRune(world, x, y, z, velX, velY, velZ);
						break;
					case BOSS_TEAR:
						particle = new ParticleGhastTear(world, x, y, z, velX, velY, velZ, Items.GHAST_TEAR);
						break;
					case GHAST_TRAP:
						particle = new ParticleGhastTrap(world, x, y, z, velX, velY, velZ);
						break;
					case PROTECTION:
						particle = new ParticleProtection(world, x, y, z, velX, velY, velZ);
						break;
					case SNOW:
						particle = new ParticleSnow(world, x, y, z, velX, velY, velZ);
						break;
					case SNOW_GUARDIAN:
						particle = new ParticleSnowGuardian(world, x, y, z, velX, velY, velZ, 0.75F);
						break;
					case SNOW_WARNING:
						particle = new ParticleSnowWarning(world, x, y, z, velX, velY, velZ, 1F);
						break;
					case ICE_BEAM:
						particle = new ParticleIceBeam(world, x, y, z, velX, velY, velZ, 0.75F);
						break;
					case ANNIHILATE:
						particle = new ParticleAnnihilate(world, x, y, z, velX, velY, velZ, 0.75F);
						break;
					case HUGE_SMOKE:
						particle = new ParticleSmokeScale(world, x, y, z, velX, velY, velZ, 4.0F + world.rand.nextFloat());
				}

				if (particle != null) {
					mc.effectRenderer.addEffect(particle);
				}
			}
		}
	}

	@Override
	public ModelBiped getKnightlyArmorModel(EntityEquipmentSlot armorSlot) {
		return knightlyArmorModel.get(armorSlot);
	}

	@Override
	public ModelBiped getPhantomArmorModel(EntityEquipmentSlot armorSlot) {
		return phantomArmorModel.get(armorSlot);
	}

	@Override
	public ModelBiped getYetiArmorModel(EntityEquipmentSlot armorSlot) {
		return yetiArmorModel.get(armorSlot);
	}

	@Override
	public ModelBiped getArcticArmorModel(EntityEquipmentSlot armorSlot) {
		return arcticArmorModel.get(armorSlot);
	}


	@Override
	public ModelBiped getFieryArmorModel(EntityEquipmentSlot armorSlot) {
		return this.fieryArmorModel.get(armorSlot);
	}

	public boolean isDangerOverlayShown() {
		return isDangerOverlayShown;
	}

	public void setDangerOverlayShown(boolean isDangerOverlayShown) {
		this.isDangerOverlayShown = isDangerOverlayShown;

	}

	@Override
	public void doBlockAnnihilateEffect(World world, BlockPos pos) {
		for (int dx = 0; dx < 4; ++dx) {
			for (int dy = 0; dy < 4; ++dy) {
				for (int dz = 0; dz < 4; ++dz) {
					double d0 = (double) pos.getX() + ((double) dx + 0.5D) / (double) 4;
					double d1 = (double) pos.getY() + ((double) dy + 0.5D) / (double) 4;
					double d2 = (double) pos.getZ() + ((double) dz + 0.5D) / (double) 4;

					double gx = world.rand.nextGaussian() * 0.2D;
					double gy = world.rand.nextGaussian() * 0.2D;
					double gz = world.rand.nextGaussian() * 0.2D;

					TwilightForestMod.proxy.spawnParticle(world, TFParticleType.ANNIHILATE, d0, d1, d2, gx, gy, gz);
				}
			}
		}
	}

}
