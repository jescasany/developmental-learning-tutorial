package existence;

import tracer.Trace;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction020;

/**
 * An Existence020 is a sort of Existence010 in which each Interaction has a predefined Valence.
 * When a given Experience is performed and a given Result is obtained, the corresponding Interaction is considered enacted.
 * The Existence020 is PLEASED when the enacted Interaction has a positive or null Valence, and PAINED otherwise.
 * An Existence020 is still a single entity rather than being split into an explicit Agent and Environment. 
 * An Existence020 demonstrates a rudimentary decisional mechanism and a rudimentary learning mechanism.
 * It learns to choose the Experience that induces an Interaction that has a positive valence.  
 * Try to change the Valences of interactions and the method giveResult(experience) 
 * and observe that the Existence020 still learns to enact interactions that have positive valences.  
 */
public class Existence020 extends Existence010 {

	@Override
	protected void initExistence(){
		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		/** Change the valence of interactions to change the agent's motivation */
		addOrGetPrimitiveInteraction(e1, r1, -1);  
		addOrGetPrimitiveInteraction(e1, r2, 1);
		addOrGetPrimitiveInteraction(e2, r1, -1);
		addOrGetPrimitiveInteraction(e2, r2, 1);		
	}
	
	@Override
	public Experience chooseExperience(Result result){
		
		Experience previousExperience = this.getPreviousExperience();
		Experience nextExperience = null;
		if (previousExperience == null)
			nextExperience = this.getOtherExperience(null);
		else{ 
			int mood = getInteraction(previousExperience.getLabel() + result.getLabel()).getValence();
			if (mood >= 0){
				Trace.addEventElement("mood", "PLEASED");
				nextExperience = previousExperience;
			}
			else{
				Trace.addEventElement("mood", "PAINED");
				nextExperience = this.getOtherExperience(previousExperience);
			}
		}
		return nextExperience;
	}
	
	/**
	 * Create an interaction as a tuple <experience, result>.
	 * @param experience: The experience.
	 * @param result: The result.
	 * @param valence: the interaction's valence
	 * @return The created interaction
	 */
	protected Interaction020 addOrGetPrimitiveInteraction(Experience experience, Result result, int valence) {
		Interaction020 interaction = (Interaction020)addOrGetInteraction(experience.getLabel() + result.getLabel()); 
		interaction.setExperience(experience);
		interaction.setResult(result);
		interaction.setValence(valence);
		return interaction;
	}
	
	@Override
	protected Interaction020 createInteraction(String label){
		return new Interaction020(label);
	}

	@Override
	protected Interaction020 getInteraction(String label){
		return (Interaction020)INTERACTIONS.get(label);
	}
	
}