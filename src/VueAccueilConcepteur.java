import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.awt.*;

import javax.swing.*;

/**
 * Vue permettant l'affichage de l'accueil Concepteur
 * @author Sofiane et Lucas
 *
 */
@SuppressWarnings("serial")
public class VueAccueilConcepteur extends JPanel {
	ControleurConcepteur cc;
	ArrayList<Questionnaire> listequestionnaire;
	EasySond sond;
	VueCreationConcepteur vuecreationConcepteur;
	VueAjoutSociete vueAjoutSociete;
	VueCreationQuestionnaire vueCreationQuestionnaire;
	VueAjoutQuestionnaire vueAjoutQuestionnaire;
	/**
	 * Constructeur de l'accueil Concepteur
	 */
	public VueAccueilConcepteur(EasySond sond) {
		super();
		this.setLayout(new BorderLayout());
		this.sond = sond;
		this.cc = new ControleurConcepteur(this);
		this.listequestionnaire = this.sond.basededonnes.BDConcepteur.listeDesQuestionnaires(this.sond.idU);
		enTete();
		body();

	}
	/**
	 * Méthode qui va modifier la vue avec la vue des information en fonction d'un questionnaire
	 * @param Int l'identifiant du questionnaire
	 */
	void afficherVueInfoQuestionnaire(int num){
		Container cont=this.sond.getContentPane();
		cont.removeAll();
		this.vuecreationConcepteur = new VueCreationConcepteur(this.sond,num);
		cont.add(vuecreationConcepteur);
		cont.validate();
		cont.repaint();
	}
	/**
	 * Méthode qui va modifier la vue avec la vue de l'accueil du Questionnaire en fonction d'un questionnaire
	 * @param EasySond, Int L'application, l'identifiant du questionnaire
	 */
	void afficherVueCreationQuestionnaire(EasySond sond, int num){
		Container cont=this.sond.getContentPane();
		cont.removeAll();
		this.vueCreationQuestionnaire = new VueCreationQuestionnaire(this.sond,num);
		cont.add(vueCreationQuestionnaire);
		cont.validate();
		cont.repaint();
	}
	/**
	 * Méthode qui va modifier la vue avec la vue de l'ajout d'un Client
	 * @param EasySond, Int L'application, l'identifiant du questionnaire
	 */
	void afficherVueAjoutSociete(EasySond sond){
		Container cont=this.sond.getContentPane();
		cont.removeAll();
		this.vueAjoutSociete = new VueAjoutSociete(this.sond);
		cont.add(vueAjoutSociete);
		cont.validate();
		cont.repaint();
	}
	/**
	 * Méthode qui va modifier la vue avec la vue de l'ajout d'un Questionnaire
	 * @param EasySond, Int L'application, l'identifiant du questionnaire
	 */
	public void afficherVueAjoutQuestionnaire(EasySond sond2) {
		Container cont=this.sond.getContentPane();
		cont.removeAll();
		this.vueAjoutQuestionnaire = new VueAjoutQuestionnaire(this.sond);
		cont.add(vueAjoutQuestionnaire);
		cont.validate();
		cont.repaint();
	}
	/**
	 * Méthodes qui vont ajouter la vue 
	 */
	private void enTete(){
		VueEnTete haut=new VueEnTete(this.sond, "Accueil Concepteur","Concepteur",this.sond.Nom,this.sond.Prenom);
		this.add(haut,"North");
	}
	/**
	 * Méthodes qui vont ajouter la vue 
	 */
	private void body(){
		JPanel principal= new JPanel();
		principal.setLayout(new BoxLayout(principal,BoxLayout.Y_AXIS));
		principal.setBackground(this.sond.couleur);
		VueScrollPan scroll1=new VueScrollPan("A créer");
		VueScrollPan scroll2=new VueScrollPan("En cours");
		principal.add(scroll1);
		principal.add(scroll2);
		for(Questionnaire q:listequestionnaire){
			if(this.sond.basededonnes.BDConcepteur.vide(q)){
			scroll(scroll1.getPanel(),q.getTitreQuestionnaire(), q.getNumeroQuestionnaire(),"Creer");
			}
			else{
				scroll(scroll2.getPanel(),q.getTitreQuestionnaire(), q.getNumeroQuestionnaire(),"Modifier");
			}
		}
		JPanel master = new JPanel();
		
		JPanel ajout = new JPanel();
		ajout.setBackground(this.sond.couleur);
		JButton ajouter = new JButton("Ajouter Client");
		ajouter.setName("ajout");
		ajouter.addActionListener(this.cc);
		ajout.add(ajouter);
		master.add(ajout, "East");
		
		JPanel ajoutq = new JPanel();
		ajoutq.setBackground(this.sond.couleur);
		JButton ajouterq = new JButton("Ajouter Questionnaire");
		ajouterq.setName("ajoutq");
		ajouterq.addActionListener(this.cc);
		ajoutq.add(ajouterq);
		master.add(ajoutq, "East");
		master.setBackground(this.sond.couleur);
		principal.add(master);
        this.add(principal,"Center");

    }
	/**
	 * Méthodes qui vont ajouter la vue 
	 */
	private void scroll(JPanel p,String label,int num, String bouton){
		JPanel c=new JPanel();
		c.setPreferredSize(new Dimension(50,50));
		c.setBackground(this.sond.couleur);
    	c.setLayout(new GridLayout(1,2));
    	c.add(new JPanel().add(new JLabel(label)));
    	JButton jbouton = new JButton(bouton);
			jbouton.setName("" + num);
    	jbouton.addActionListener(this.cc);
    	JButton jboutons = new JButton("Supprimer");
    	jboutons.setName("" + num);
    	jboutons.addActionListener(this.cc);
    	c.add(new JPanel().add(jbouton));
    	c.add(new JPanel().add(jboutons));
				p.setBackground(this.sond.couleur);
        p.add(c);
	}

}
