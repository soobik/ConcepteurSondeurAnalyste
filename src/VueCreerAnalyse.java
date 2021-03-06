import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class VueCreerAnalyse extends JPanel{
	ModeleAnalyste modele;
	VueAccueilAnalyste vaa;
	String idQ;
	ArrayList<Question> listeQuestion;
	VueEtudeQuestion vueEtudeQuestion;
	ControleurEtudeQuestion ceq;
	ControleurCreerAnalyste cca;
	HashMap<Integer,String> map;
	VueCreerAnalyse(String idQ,VueAccueilAnalyste vaa){
		super();
		this.idQ=idQ;
		this.vaa=vaa;
		this.map = new HashMap<Integer,String>();
		this.ceq=new ControleurEtudeQuestion(this);
		this.cca=new ControleurCreerAnalyste(this.vaa);
		this.modele=new ModeleAnalyste(this.vaa.sond.basededonnes);
		this.listeQuestion=this.modele.listeDesQuestion(Integer.parseInt(this.idQ));
		creerFenetre();
		remplirMap();
	}
	
	public void creerFenetre(){
		Container cont = this.vaa.sond.getContentPane();
		cont.removeAll();
		this.setLayout(new BorderLayout());
		this.add(enTete(),"North");
		this.add(corps(),"Center");
		this.add(boutonsBas(),"South");
		cont.add(this);
		cont.validate();
		cont.repaint();
	}
	
	
	private Component enTete(){
		// Le panel haut
		VueEnTete haut = new VueEnTete(this.vaa.sond,"Création d'une analyse","Analyste",this.vaa.sond.Nom,this.vaa.sond.Prenom);
		return haut;
	}
	
	
	private Component corps(){
		int i=0;
		JPanel retour = new JPanel();
		retour.setLayout(new BorderLayout());
		JPanel bidonGauche = new JPanel();
		JPanel conteneurGeneral = new JPanel();
		conteneurGeneral.setLayout(new BoxLayout(conteneurGeneral,BoxLayout.Y_AXIS));
		JLabel titre = new JLabel("Liste des questions                           ");
		titre.setFont(new Font("Arial",Font.BOLD,30));
		conteneurGeneral.add(titre);
		JScrollPane scrollerQuestions = new JScrollPane(conteneurGeneral,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		for(Question q:this.listeQuestion){
			i++;
			JPanel cGeneral=new JPanel();
			JPanel cQuestion=new JPanel();
			JPanel cBoutons=new JPanel();
			
			cQuestion.setLayout(new BoxLayout(cQuestion,BoxLayout.Y_AXIS));cGeneral.setLayout(new GridLayout(1,2));
			cBoutons.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			JButton boutton1 = new JButton("Selectionner");
			boutton1.addActionListener(this.ceq);
			boutton1.setName(""+q.getIdQuestion());

			cQuestion.add(new JLabel(" "));
			cQuestion.add(new JLabel("       Question "+q.getIdQuestion()));
			cQuestion.add(new JLabel(" "));
			cQuestion.add(new JLabel("       "+q.getTexteQuestion()));
			cQuestion.add(new JLabel(" "));
			cQuestion.add(new JLabel("  "));
			cBoutons.add(new JLabel("            "));
			cBoutons.add(boutton1);
			conteneurGeneral.add(new JLabel("      "));
			cGeneral.add(cQuestion);
			cGeneral.add(cBoutons);
			cGeneral.setBorder(BorderFactory.createLineBorder(Color.black));
			conteneurGeneral.add(cGeneral);
		}
		bidonGauche.add(new JLabel("                          "));
		bidonGauche.setBackground(new Color(78,217,255));
		retour.add(bidonGauche, "West");
		retour.add(scrollerQuestions, "Center");
		return retour;
	}
	
	private Component boutonsBas(){
		JPanel conteneurBoutons = new JPanel();
		JPanel contB2 = new JPanel();
		JPanel contB4 = new JPanel();
		JButton quitter = new JButton("Retour");
		quitter.addActionListener(cca);
		
		contB2.add(quitter);
		contB4.add(new JButton("Envoyer"));
		contB2.setBackground(new Color(78,217,255));
		contB4.setBackground(new Color(78,217,255));
		
		conteneurBoutons.add(contB2);
		conteneurBoutons.add(contB4);
		conteneurBoutons.setBackground(new Color(78,217,255));
		return conteneurBoutons;
	}
	public void afficherVueEtudeQuestion(String nom) {
		Container cont = this.vaa.sond.getContentPane();
		cont.removeAll();
		Question temp =this.modele.getQuestion(Integer.parseInt(idQ), Integer.parseInt(nom));
		this.vueEtudeQuestion = new VueEtudeQuestion(this.vaa.sond,temp,this,this.map.get(temp.getIdQuestion()));
		cont.add(vueEtudeQuestion);
		cont.validate();
		cont.repaint();
	}
	
	void remplirMap(){
		for(Question elem:this.listeQuestion){
			this.map.put(elem.getIdQuestion()," ");
		}
	}
	
	void setMap(String s,Integer i){
		this.map.replace(i,s);
	}
}
