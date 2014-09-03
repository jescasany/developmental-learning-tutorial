package coupling;

import coupling.interaction.Interaction040;

public class Experience040 extends Experience {

	/**
	 * The experience's interaction.
	 */
	private Interaction040 interaction;
	
	public Experience040(String label){
		super(label);
	}

	public boolean isPrimitive(){
		return (this.interaction == null);
	}
	
	public void setInteraction(Interaction040 interaction){
		this.interaction = interaction;
	}
	
	public Interaction040 getInteraction(){
		return this.interaction;
	}

}