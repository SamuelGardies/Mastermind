
	package MasterMind_Daugreilh_Gardiès;

	import java.util.Scanner;

	public class Mastermind_Daugreilh_Gardiès 
	{
		public static final String[] Couleurs= {"Vert", "Bleu", "Rouge", "Jaune", "Orange", "Gris", "Marron", "Noir"};
		
		public static int[] tableau (int min, int max, int longueur)
		{//crée un tableau de la longueur choisie dans un intervalle donné
			int[] tab = new int[longueur];
			int i;
			for (i=0;i<longueur;i++)
				// On remplit chaque case par un nombre entier aléatoire compris dans l'intervalle
				tab[i]=(int)(Math.random() * (max - min) + min);
			return tab;
		}
		
		public static String descriptionTableau (String[] tab)
		{//Renvoie le tableau sous forme d'une chaîne de cractères
			String monTab = "{" + tab[0];// On commence par une accolade
			for (int i=1; i<tab.length;i++)
				monTab+= ", "+ tab[i]; // On sépare d'une virgule et d'un espace tous les éléments du tableau
			monTab+="}";// On termine par une accolade
			return monTab;		
		}

		public static String[] Couleuraléatoire (int[] tab)
		{//Associe des entiers aux couleurs du jeu
			String[] tirage=new String[4];
			int i;
			for(i=0;i<4;i++)
				switch (tab[i])//chaque entier possible est associé à une couleur proposée
				{
				case 1 :
					tirage[i]=Couleurs[0];
					break;
				case 2 :
					tirage[i]=Couleurs[1];
					break;
				case 3 :
					tirage[i]=Couleurs[2];
					break;
				case 4 :
					tirage[i]=Couleurs[3];
					break;
				case 5 :
					tirage[i]=Couleurs[4];
					break;
				case 6 :
					tirage[i]=Couleurs[5];
					break;
				case 7 : 
					tirage[i]=Couleurs[6];
					break;
				case 8 :
					tirage[i]=Couleurs[7];
					break;	
				default :
					break;				
			}
			
			return tirage;
		}
		
		public static String[] CouleurChoisie(String Initiales)
		{//Associe des Initiales aux couleurs du jeu correspondantes
			String[] Choix= new String[4];
			String[] tabInitiales =new String[4];
			for (int i=0;i<4;i++)
			{
				tabInitiales[i]=String.valueOf(Initiales.charAt(i));
				switch (tabInitiales[i]) //Chaque initiale est liée à sa couleur correspondante 
				{
				case "v" :
					Choix[i]=Couleurs[0];
					break;
				case "b" :
					Choix[i]=Couleurs[1];
					break;
				case "r" :
					Choix[i]=Couleurs[2];
					break;
				case "j" :
					Choix[i]=Couleurs[3];
					break;
				case "o" :
					Choix[i]=Couleurs[4];
					break;
				case "g" :
					Choix[i]=Couleurs[5];
					break;
				case "m" :
					Choix[i]=Couleurs[6];
					break;
				case "n" :
					Choix[i]=Couleurs[7];
					break;
				default :
					Choix[i]="0";
					
				}
			}
			return Choix;
			
		}

		public static String[] ChoixUser (Scanner clavier)
		{//demande les initiales des couleurs voulues et renvoie le tableau avec les couleurs voulues
			String[] Choix=new String[4];
			String Initiales;
			boolean verif=true;
			while (verif)
			{
				System.out.println("Entrez les initiales de votre combinaison de couleurs parmi celles-ci :");
				System.out.println(descriptionTableau(Couleurs));
				Initiales=clavier.nextLine();/*clavier.nextline() est la chaine de caractères issue de la 
											 *saisie au clavier										
											 */
				if (Initiales.length()==4)
				{
					Choix=CouleurChoisie(Initiales); // Pour chaque initiale, on l'associe à sa couleur
					verif=TestCouleur(Choix);//Pour chaque couleur, on vérifie qu'elle existe
				}
				else
					System.out.println("Votre combinaison est trop longue ou trop courte, écrivez exactement 4 couleurs.");
					//Robustesse longueur de combinaison
			}
			
			return Choix;
		}
		
		public static boolean TestCouleur (String[] user)
		{//Ce sous-programme sert à la robustesse si l'utilisateur rentre un caractère non compris dans le jeu
			boolean PasPrésente=false;
			int CouleursNonPrésentes;
			int i=0;
			while (i<4)
			{
				CouleursNonPrésentes=0;
				for(int j=0;j<8;j++)
					if (!user[i].equals(Couleurs[j]))//On chaque couleur 
						CouleursNonPrésentes+=1;
				if (CouleursNonPrésentes==8) //si aucune couleur n'est présente on renvoie un message
				{
					PasPrésente=true;
					System.out.println("La couleur n°"+ (i+1) + " que vous avez demandé n'est pas comprise dans le jeu.");
				}

				i++;
			}
			return PasPrésente;
		}
		
		public static int bienplacé (String[] proposition,String solution[])
		{//Compte le nombre de pions bien placés dans une proposition
			int bp=0;
			for (int i=0;i<4;i++)
					if(proposition[i].equals(solution[i]))/*Si la couleur est au même endroit dans la solution 
														   *que dans la proposition, alors le pion est bien placé*/
						bp+=1;
			return bp;
		}

		public static int malplacé (String[] joueur, String[] solution)
		{//Compte le nombre de pions mal placés dans une proposition
			int mp=0;
			for(int j=0;j<4;j++)
				if(!joueur[j].equals(solution[j]))
					for(int k=0;k<4;k++)
						if (joueur[j].equals(solution[k]))/*Si le pion choisi n'est pas à la bonne place mais
						 								   *est présent dans la solution, alors le compteur de 
						 								   *mal placé augmente */
						{
							mp+=1;
							break;
						}
			return mp;
		}
		
		
		
		public static String[] remplirhistorique (String[] historique,String[][] propositions, String[] Choix, int n, int bp, int mp)
		{//Ce sous-programme sert à remplir l'historique des propositions de l'utilisateur
			for (int i=0;i<4;i++)
				propositions[n][i]=Choix[i];
			historique[n]=descriptionTableau(propositions[n])+" "+bp+" bien placé(s), "+mp+" mal placé(s)";
			//on remplit l'historique avec la proposition et le nombre de pions bien ou mal placés
			return historique;
		}
		
		public static void afficherhistorique (String[] historique, int nbtours)
		{//Ce sous programme afiche tout simplement l'historique
			for (int i=0; i<nbtours;i++)
				System.out.println(historique[i]);
			
		}
		public static String[] Trouvercouleurs (String[] solution)
		{//Trouve les couleurs de la solution et renvoie un tableau avec ces couleurs
			int[] proposition= new int[4];
		    int[] bonnesCouleurs= new int[0];
		    String[] BonnesCouleursStr=new String[4];
		    int bienPlacés=0;
		    int nombreDePropositions=0;
		    int nombreCouleurTrouvée=0;
		    int i;  
		    /* trouver les bonnes couleurs */
		    for(i=1;i<=8;i++)
		    {
		    	//Pour chaque couleur on va vérifier si elles sont présentes dans la combinaison secrète et combien de fois 
		    	proposition[0]=i;proposition[1]=i;proposition[2]=i;proposition[3]=i;
		        bienPlacés=bienplacé(Couleuraléatoire(proposition),solution);
		        nombreCouleurTrouvée=nombreCouleurTrouvée+bienPlacés;
		        switch(bienPlacés)
		        {//On ajoute autant de cases au tableau que le nombre d'occurences de la couleur correspondante
		            case 1:
		                bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 break;
		             case 2:
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                break;
		             case 3:
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 break;
		             case 4:
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 break;
		             default:
		                 break;
		        }
		        nombreDePropositions+=1;
		        if(nombreCouleurTrouvée==4)
		        	break;
		        }
		    	System.out.println("L'étape préliminaire a pris " + nombreDePropositions +" tours.");
		    	BonnesCouleursStr=Couleuraléatoire(bonnesCouleurs);//on attribue les couleurs aux chiffres trouvés
		    	System.out.println(descriptionTableau(BonnesCouleursStr));
		    	return BonnesCouleursStr;
		}
		public static int[] tourDuJoueur(String[] proposition, Scanner reponse)        
	    /* ce sous-programme contient le déroulement du tour du joueur, par soucis de lisibilité, nous avons préféré faire un sous-programme dédié plutôt que de copier/coller le code pour chaque version du mastermind inversé */
	    {
	    int bienPlacés=0;
	    int malPlacés=0;
	    int[] rep=new int[2];
	    System.out.println("Proposition : "+(descriptionTableau(proposition)));    /* à chaque proposition, l'ordinateur demande au joueur d'indiquer le nombre de pions bien placés et mal placés (ce fonctionnement sera repris dans les versions suivantes du programme) */
	     System.out.println("Entrez le nombre de pions bien placés.");
	     bienPlacés=Integer.parseInt(reponse.nextLine());
	         
	     /* Robustesse */                                                                        /* le joueur ne doit pas pouvoir entrer un nombre de pions bien placés supérieur à 4 */
	     while(bienPlacés>4)
	         {System.out.println("Erreur de saisie, veuillez entrer un nombre infèrieur ou égal à 4.");
	         System.out.println("Proposition : "+(descriptionTableau(proposition)));
	         System.out.println("Entrez le nombre de pions bien placés.");
	         bienPlacés=Integer.parseInt(reponse.nextLine());}
	     /*-------------*/
	         
	     System.out.println("Entrez le nombre de pions mal placés.");
	     malPlacés=Integer.parseInt(reponse.nextLine());
	     
	     /* Robustesse */                                                                        /* le joueur ne doit pas pouvoir entrer un nombre de pions mal placés supérieur à 4 */
	     while(malPlacés>4)
	         {System.out.println("Erreur de saisie, veuillez entrer un nombre infèrieur ou égal à 4.");
	         System.out.println("Proposition : "+(descriptionTableau(proposition)));
	         System.out.println("Entrez le nombre de pions mal placés.");
	          malPlacés=Integer.parseInt(reponse.nextLine());}
	     /*-------------*/        
	     
	     /* Robustesse */    
	     while(bienPlacés+malPlacés>4)                                                            /* le joueur ne doit pas pouvoir entrer un nombre de pions total (bien et mal placés) supérieur à 4 */
	         {System.out.println("Erreur de saisie, le nombre de pions bien et mal placés doit être inférieur ou égal à 4.");
	         System.out.println("Proposition : "+(descriptionTableau(proposition)));    
	         System.out.println("Entrez le nombre de pions bien placés.");                        /* si c'est le cas, on lui redemande d'entrer le nombre de pions bien et mal placés */
	         bienPlacés=Integer.parseInt(reponse.nextLine());
	         while(bienPlacés>4)                                                                    /* en faisant attention qu'il ne puisse toujours pas entrer un nombre de pions bien OU mal placés supérieur à 4 */
	            {System.out.println("Erreur de saisie, veuillez entrer un nombre infèrieur ou égal à 4.");
	             System.out.println("Proposition : "+(descriptionTableau(proposition)));
	             System.out.println("Entrez le nombre de pions bien placés.");
	             bienPlacés=Integer.parseInt(reponse.nextLine());}
	         System.out.println("Entrez le nombre de pions mal placés.");
	         malPlacés=Integer.parseInt(reponse.nextLine());
	         while(malPlacés>4)
	             {System.out.println("Erreur de saisie, veuillez entrer un nombre infèrieur ou égal à 4.");
	             System.out.println("Proposition : "+(descriptionTableau(proposition)));
	             System.out.println("Entrez le nombre de pions mal placés.");
	              malPlacés=Integer.parseInt(reponse.nextLine());}}
	    /*-------------*/
	     
	    rep[0]=bienPlacés;
	    rep[1]=malPlacés;
	    return rep;
	    }
		public static String[] TrouvercouleursM3 (Scanner clavier)
		{/*Renvoie un tableau avec les couleurs de la solution à l'intérieur, nuance avec le TrouverCouleurs :
		*ici c'est l'utilisteur qui indique le nombre de pions bien placés
		*/
			int[] proposition= new int[4];
		    int[] bonnesCouleurs= new int[0];
		    String[] BonnesCouleursStr=new String[4];
		    int bienPlacés=0;
		    int[] BienetMalPlacés= new int[2];
		    int nombreDePropositions=0;
		    int nombreCouleurTrouvée=0;
		    int i;  
		    /* trouver les bonnes couleurs */
		    for(i=1;i<=8;i++)
		    {
		    	//Pour chaque couleur on va vérifier si elles sont présentes dans la combinaison secrète et combien de fois 
		    	proposition[0]=i;proposition[1]=i;proposition[2]=i;proposition[3]=i;
		        BienetMalPlacés=tourDuJoueur(Couleuraléatoire(proposition),clavier);
		    	bienPlacés=BienetMalPlacés[0];
		        nombreCouleurTrouvée=nombreCouleurTrouvée+bienPlacés;
		        switch(bienPlacés)
		        {//On ajoute autant de cases au tableau que le nombre d'occurences de la couleur correspondante
		            case 1:
		                bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 break;
		             case 2:
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                break;
		             case 3:
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 break;
		             case 4:
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 bonnesCouleurs=ajouterPremiereCase(bonnesCouleurs,i);
		                 break;
		             default:
		                 break;
		        }
		        nombreDePropositions+=1;
		        if(nombreCouleurTrouvée==4)
		        	break;
		        }
		    	System.out.println("L'étape préliminaire a pris " + nombreDePropositions +" tours.");
		    	BonnesCouleursStr=Couleuraléatoire(bonnesCouleurs);//on attribue les couleurs aux chiffres trouvés
		    	System.out.println(descriptionTableau(BonnesCouleursStr));
		    	return BonnesCouleursStr;
		}
		
		
		
		
		public static int[] ajouterPremiereCase(int[] tab, int Case)    
	    /* ce sous-programme ajoute une case voulue au début d'un tableau */
	    {
	    int[] res=new int[tab.length+1];     /* on créé un tableau plus grand d'une case que celui en paramètre */
	    System.arraycopy(tab,0,res,1,tab.length);  /* on copie ensuite dedans le tableau en paramètre, 
	    											*à partir de la 2em case du tableau res*/
	    res[0]=Case;                /* enfin on ajoute la premiere case qui est donnée en paramètre */
	    return res;
	    }
		
		
		public static void JeuM1 (String[] solution, int maxtour,Scanner clavier)
		{// c'est le sous programme qui régit le jeu du Mode 1
			boolean fin=false;
			boolean verif=false;
			int nbtours=0;
			int bp = 0;
			int mp = 0;
			String[] joueur=new String[4];
			String[] historique=new String[maxtour];
			String[][] propositions = new String[maxtour][4];	
			while ((!fin)&&(nbtours<maxtour))
			{		
				System.out.println("Tour n°" + (nbtours+1) +" sur " +(maxtour) );
				joueur=ChoixUser(clavier);
				verif =TestCouleur(joueur);
				while (verif)/*Si les initiales données par le joueur ne sont pas comprises dans le jeu, 
								on lui demande de re saisir une combinaison*/
				{
					joueur=ChoixUser(clavier);
					verif =TestCouleur(joueur);
				}
				System.out.println("Vous avez choisi de tester la combinaison suivante : ");
				System.out.println(descriptionTableau(joueur));
				bp=bienplacé(joueur, solution);
				mp=malplacé(joueur, solution);
				if (bp>0)
					System.out.println("Il y a " + bp +" pions bien placé(s).");
				if (mp>0)
					System.out.println("Il y a " + mp +" pions mal placé(s).");
				if((bp==0)&&(mp==0))
					System.out.println("Aucune couleur proposée n'est contenue dans la combinaison secrète.");
				if(bp==4)
				{
					fin=true;
					System.out.println("Félicitations ! Vous avez réussi à trouver la bonne combinaison en "+nbtours+" tour(s).");
					System.out.println("La combinaison secrète créée par l'ordi était :");
					System.out.println(descriptionTableau(solution));
				}
				historique=remplirhistorique(historique,propositions,joueur,nbtours,bp,mp);
				if (nbtours>0)
				{
					System.out.println("Historique des choix :");
					afficherhistorique(historique,nbtours);
				}
				nbtours++;
			}
			if ((nbtours==maxtour)&&(!fin))
				System.out.println("Nombre de tours maximums dépassés ! Dommage !");
				System.out.println("La réponse était :");
				System.out.println(descriptionTableau(solution));
		}

		public static void JeuM2(String[] solution, Scanner clavier)
		{//Sous programme régissant le jeu du mode 2, 
			String[] propositionfinale= new String[4];
			int compteurtours=0;
			propositionfinale=Trouvercouleurs(solution);
			int bp=0;
			int prebp=0;
			String tmp;
			bp=bienplacé(propositionfinale,solution);
			System.out.println("Historique :");
			System.out.println(descriptionTableau(propositionfinale) +" "+bp+" bien placé(s)" );
			compteurtours=1;
			prebp=bp;
			while (bp!=4)
				{
					for (int i=3;i>=1;i--)
					{/*ici la stratégie consiste à parcourir le tableau à l'envers et à intervertir deux 
					*couleurs successives.Si le nombre de pions bien placés diminue,on revient à l'arrangement
					*de départ la case d'avant.
					*/
							if (!propositionfinale[i].equals(propositionfinale[i-1]))
							{//On intervertit seulement les couleursqui ne sont pas les mêmes
								tmp=propositionfinale[i];
								propositionfinale[i]=propositionfinale[i-1];
								propositionfinale[i-1]=tmp;
								bp=bienplacé(propositionfinale,solution);
								System.out.println(descriptionTableau(propositionfinale)+ " " + bp +" bien placé(s)");
								if (bp==4)
									break;
								if (bp<prebp)
								{
									tmp=propositionfinale[i];
									propositionfinale[i]=propositionfinale[i-1];
									propositionfinale[i-1]=tmp;		
								}
								prebp=bp;
								compteurtours+=1;
							}
							else //si les couleurs i et i-1 sont les mêmes
							{
								tmp=propositionfinale[i];
								if (i!=1)
								{//On échange avec la case d'après
									propositionfinale[i]=propositionfinale[i-2];
									propositionfinale[i-2]=tmp;
								}
								else
								{//Si i=1, on intervertit avec la dernière case du tableau
									propositionfinale[i]=propositionfinale[3];
									propositionfinale[3]=tmp;
								}
									
								bp=bienplacé(propositionfinale,solution);
								System.out.println(descriptionTableau(propositionfinale)+ " " + bp +" bien placé(s)");
								if (bp==4)
									break;
								if (bp<prebp)
								{//si la combinaison précédente avait plus de biens placés, on y revient.
									tmp=propositionfinale[i];
									propositionfinale[i]=propositionfinale[i-1];
									propositionfinale[i-1]=tmp;		
								}
								prebp=bp;
								compteurtours+=1;
								}
							
							
					}
				}
				System.out.println("C'est fini ! L'ordinateur a trouvé votre combinaison secrète en " + compteurtours + " tours :");
				System.out.println(descriptionTableau(propositionfinale));			
		}
		public static void JeuM3(Scanner clavier)
		{//Sous-programme régissant le sous-programme du mode 3
			String[] propositionfinale= new String[4];
			int compteurtours=0;
			propositionfinale=TrouvercouleursM3(clavier);
			int bp=tourDuJoueur(propositionfinale,clavier)[0];
			int prebp=0;
			String tmp;
			System.out.println("Historique :");
			System.out.println(descriptionTableau(propositionfinale) +" "+bp+" bien placé(s)" );
			compteurtours=1;
			prebp=bp;
			while (bp!=4)
				{
					for (int i=3;i>=1;i--)
					{/*ici la stratégie est la même que pour le mode 2, néanmoins, ici c'est l'utikisateur qui déclare				
					*les pions bien et mal placés 
					*/
							if (!propositionfinale[i].equals(propositionfinale[i-1]))
							{
							tmp=propositionfinale[i];
							propositionfinale[i]=propositionfinale[i-1];
							propositionfinale[i-1]=tmp;
							bp=tourDuJoueur(propositionfinale,clavier)[0];
							System.out.println(descriptionTableau(propositionfinale)+ " " + bp +" bien placé(s)");
							if (bp==4)
								break;
							if (bp<prebp)
							{
								tmp=propositionfinale[i];
								propositionfinale[i]=propositionfinale[i-1];
								propositionfinale[i-1]=tmp;		
							}
							prebp=bp;
							compteurtours+=1;
							}
							else
							{
								tmp=propositionfinale[i];
								if (i!=1)
								{
								propositionfinale[i]=propositionfinale[i-2];
								propositionfinale[i-2]=tmp;
								}
								else
								{
									propositionfinale[i]=propositionfinale[3];
									propositionfinale[3]=tmp;
								}
									
								bp=tourDuJoueur(propositionfinale,clavier)[0];
								System.out.println(descriptionTableau(propositionfinale)+ " " + bp +" bien placé(s)");
								if (bp==4)
									break;
								if (bp<prebp)
								{
									tmp=propositionfinale[i];
									propositionfinale[i]=propositionfinale[i-1];
									propositionfinale[i-1]=tmp;		
								}
								prebp=bp;
								compteurtours+=1;
								}
							
							
					}
				}
				System.out.println("C'est fini ! L'ordinateur a trouvé votre combinaison secrète en " + compteurtours + " tours :");
				System.out.println(descriptionTableau(propositionfinale));			
		}


		public static void Mode1 (Scanner clavier)
		{//On choisit ici le niveau avec lequel on souhaite jouer
			int maxtours=0;
			while (maxtours==0)	
			{
				System.out.println("4 niveaux disponibles : débutant, intermédiaire et expert et libre");
				System.out.println("Entrez l'initiale de votre niveau :");	
				String niveau=clavier.nextLine();
				switch (niveau)
				{//chaque niveau correspond à un nombre de tours maximum
					case "d" :
					{
						maxtours=50;
						break;
					}
					case "i" :
					{
						maxtours=20;
						break;
					}
					case "e" :
					{
						maxtours=10;
						break;
					}
					case "l" :
					{
						System.out.println("Entrez le nombre de tours que vous vous donnez");
						maxtours=Integer.parseInt(String.valueOf(clavier.nextLine()));
					}
					default :
						System.out.println("Erreur de saisie veuillez choisir uniquement l'initiale de votre niveau");
				}
			}
			int[] tabini = tableau(1,8,4);
			String[] TirageOrdi= Couleuraléatoire(tabini);
			JeuM1(TirageOrdi,maxtours,clavier);
		}

		public static void Mode2 (Scanner clavier)
		{//On entre la combinaison secrète dans la console et on lance le programme pour que l'ordinateur la trouve
			System.out.println("Entrez votre combinaison secrète : ");
			String[] solution=new String [4];
			solution=ChoixUser(clavier);
			System.out.println("Solution choisie");
			System.out.println(descriptionTableau(solution));
			JeuM2(solution,clavier);
		}
		
		public static void Mode3 (Scanner clavier)
		{//Ici, la solution est dans notre tête, donc on lance sans mettre de solution en paramètre
			System.out.println("Pensez à une combinaison secrète de 4 couleurs parmi celles-ci :");
			System.out.println(descriptionTableau(Couleurs));
			JeuM3(clavier);
		}
		

		public static int ChoixMode(Scanner clavier)
		{//sert à choisir le mode
			int mode=0;
			boolean verif=false;
			while (!verif)
			{
				String choix =clavier.nextLine();
				verif=false;
				if (choix.equals("1"))
				{
					mode=1;
					verif=true;
				}
				else if (choix.equals("2"))
				{
					mode=2;
					verif=true;
				}
				else if (choix.equals("3"))
				{
					mode=3;
					verif=true;
				}
				else
					System.out.println("Vous n'avez pas rentré 1,2 ou 3, veuillez réessayer.");
			}
			return mode;
		}

		public static void main(String[] args) 
		{
			Scanner clavier = new Scanner(System.in);
			System.out.println("Bienvenue dans le jeu du MasterMind de Daugreilh Bastien et Gardiès Samuel");
			System.out.println("Nous jouerons selon les règles de la consigne");
			System.out.println("Choisissez le mode de jeu que vous souhaitez");
			System.out.println("Tapez 1 si vous souhaitez trouver une combinaison choisie par l'ordinateur");
			System.out.println("Tapez 2 si vous souhaitez donner une combinaison que l'ordinateur doit trouver");
			System.out.println("Tapez 3 pour faire deviner à l'ordinateur votre combinaison secrète");
			int mode=ChoixMode(clavier);
			if (mode==1)
			{
				Mode1(clavier);
			}
			else if (mode==2) 
			{
				Mode2(clavier);
			}
			else 
			{
				Mode3(clavier);
			}
		}
	}


