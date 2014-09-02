package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import reactive.Environment2;
import tracer.Trace;
import agent.decider.Decider2;
import coupling.Experience;
import coupling.Obtention2;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction2_;

/**
 * Existence2 is a sort of Existence1 that learns composite interactions (Interaction2). 
 * Additionally, Existence2 proposes a list of interactions to the Decider.
 * 
 * @author Olivier
 */
public class Existence2_ extends Existence1_ {

	private Interaction2_ contextInteraction;
	private Interaction2_ currentInteraction;

	@Override
	protected void initExistence(){
		this.proactive = new Decider2(this);
		this.reactive = new Environment2(this);

		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createOrGetPrimitiveInteraction(e1, r1, -1);
		createOrGetPrimitiveInteraction(e1, r2, 1);
		createOrGetPrimitiveInteraction(e2, r1, -1);
		createOrGetPrimitiveInteraction(e2, r2, 1);
	}
	
	@Override
	protected void learn(){

		this.contextInteraction = this.currentInteraction;
		this.currentInteraction = ((Obtention2)this.obtention).getInteraction();
		
		if (this.currentInteraction.getValence() >= 0)
			Trace.addEventElement("mood", "PLEASED");
		else{
			Trace.addEventElement("mood", "PAINED");
		}

		if (this.contextInteraction != null )
			this.createOrGetCompositeInteraction(this.contextInteraction, this.currentInteraction);		
	}
	
	@Override
	protected Interaction2_ createNewInteraction(String label, int valence){
		return new Interaction2_(label, valence);
	}

	public Interaction2_ createOrGetCompositeInteraction(
		Interaction preInteraction, Interaction postInteraction) {
		int valence = preInteraction.getValence() + postInteraction.getValence();
		Interaction2_ interaction = (Interaction2_)createOrGet(preInteraction.getLabel() + postInteraction.getLabel(), valence); 
		interaction.setPreInteraction(preInteraction);
		interaction.setPostInteraction(postInteraction);
		System.out.println("learn " + interaction.toString());
		return interaction;
	}

	public List<Interaction> affordedInteractions(){
		List<Interaction> interactions = new ArrayList<Interaction>();
		if (this.currentInteraction != null){
			for (Interaction activatedInteraction : this.getActivatedInteractions(this.currentInteraction)){
				interactions.add(((Interaction2_)activatedInteraction).getPostInteraction());
				System.out.println("afforded " + ((Interaction2_)activatedInteraction).getPostInteraction().getLabel());
			}
		}
		Collections.sort(interactions);
		return interactions;
	}

	protected List<Interaction> getActivatedInteractions(Interaction interaction) {
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
		for (Interaction activatedInteraction : this.INTERACTIONS.values())
			if (interaction == ((Interaction2_)activatedInteraction).getPreInteraction())
				activatedInteractions.add((Interaction2_)activatedInteraction);
		return activatedInteractions;
	}	

}