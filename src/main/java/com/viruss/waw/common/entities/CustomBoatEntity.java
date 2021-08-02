package com.viruss.waw.common.entities;

import com.viruss.waw.utils.registries.ModRegistry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

@SuppressWarnings("all")
public class CustomBoatEntity extends Boat {
    private static final EntityDataAccessor<String> DATA_TYPE = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.STRING);
    private Item boatItem;

    public CustomBoatEntity(EntityType<? extends Boat> p_i50129_1_, Level p_i50129_2_) {
        super(p_i50129_1_, p_i50129_2_);
    }

    public CustomBoatEntity(Item drop,EntityType<? extends Boat> p_i50129_1_, Level p_i50129_2_) {
        this(p_i50129_1_, p_i50129_2_);
        this.boatItem = drop;
    }

    public CustomBoatEntity(Item drop,Level worldIn, double x, double y, double z) {
        this(drop, ModRegistry.ENTITIES.getBoatEntity(), worldIn);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TYPE, "pattern");
    }

    public String getDataType() {
        return entityData.get(DATA_TYPE);
    }

    public void setDataType(String type) {
        entityData.set(DATA_TYPE,type);
    }

    @Override
    public Item getDropItem() {
        Item drop = boatItem;
        if(drop== null)
            drop = super.getDropItem();
        return drop;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}