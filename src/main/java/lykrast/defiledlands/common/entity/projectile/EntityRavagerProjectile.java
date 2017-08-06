package lykrast.defiledlands.common.entity.projectile;

import lykrast.defiledlands.common.init.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityRavagerProjectile extends EntitySmallFireball {
	private float damage, speed;

	public EntityRavagerProjectile(World worldIn) {
		super(worldIn);
		damage = 12.0F;
		speed = 1.0F;
	}

	public EntityRavagerProjectile(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ, float speed) {
		super(worldIn, shooter, accelX, accelY, accelZ);
        double d0 = (double)MathHelper.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
        this.accelerationX = accelX / d0 * 0.2D * (double)speed;
        this.accelerationY = accelY / d0 * 0.2D * (double)speed;
        this.accelerationZ = accelZ / d0 * 0.2D * (double)speed;
        this.damage = 12.0F;
        float f = 1.0F - (0.05F / speed);
		this.speed = f;
	}

	public EntityRavagerProjectile(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ, float speed) {
		super(worldIn, x, y, z, accelX, accelY, accelZ);
        this.accelerationX *= (double)speed;
        this.accelerationY *= (double)speed;
        this.accelerationZ *= (double)speed;
		this.damage = 12.0F;
        float f = 1.0F - (0.05F / speed);
		this.speed = f;
	}
	
	/**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote)
        {
            if (result.entityHit != null)
            {
            	boolean flag = result.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.shootingEntity), getDamage());

            	if (flag)
            	{
            		this.applyEnchantments(this.shootingEntity, result.entityHit);
            		if (result.entityHit instanceof EntityLivingBase)
            			((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(ModPotions.vulnerability, 200));
            	}
            }

            this.setDead();
        }
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
    	super.writeEntityToNBT(compound);
    	compound.setFloat("damage", damage);
    	compound.setFloat("speed", speed);
    }
    
    public void readEntityFromNBT(NBTTagCompound compound)
    {
    	super.readEntityFromNBT(compound);
    	damage = compound.getFloat("damage");
    	speed = compound.getFloat("speed");
    }

    /**
     * Return the motion factor for this projectile. The factor is multiplied by the original motion.
     */
    protected float getMotionFactor()
    {
        return getSpeed();
    }

    public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	protected boolean isFireballFiery()
    {
        return false;
    }

}