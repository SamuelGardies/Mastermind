
	package MasterMind_Daugreilh_Gardi�s;

	import java.util.Scanner;

	public class Mastermind_Daugreilh_Gardi�s 
	{
		public static final String[] Couleurs= {"Vert", "Bleu", "Rouge", "Jaune", "Orange", "Gris", "Marron", "Noir"};
		
		public static int[] tableau (int min, int max, int longueur)
		{//cr�e un tableau de la longueur choisie dans un intervalle donn�
			int[] tab = new int[longueur];
			int i;
			for (i=0;i<longueur;i++)
				// On remplit chaque case par un nombre entier al�atoire compris dans l'intervalle
				tab[i]=(int)(Math.random() * (max - min) + min);
			return tab;
		}
		
		public static String descriptionTableau (String[] tab)
		{//Renvoie le tableau sous forme d'une cha�ne de cract�res
			String monTab = "{" + tab[0];// On commence par une accolade
			for (int i=1; i<tab.length;i++)
				monTab+= ", "+ tab[i]; // On s�pare d'une virgule et d'un espace tous les �l�ments du tableau
			monTab+="}";// On termine par une accolade
			return monTab;		
		}

		public static String[] Couleural�atoire (int[] tab)
		{//Associe des entiers aux couleurs du jeu
			String[] tirage=new String[4];
			int i;
			for(i=0;i<4;i++)
				switch (tab[i])//chaque entier possible est associ� � une couleur propos�e
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
				switch (tabInitiales[i]) //Chaque initiale est li�e � sa couleur correspondante 
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
				Initiales=clavier.nextLine();/*clavier.nextline() est la chaine de caract�res issue de la 
											 *saisie au clavier										
											 */
				if (Initiales.length()==4)
				{
					Choix=CouleurChoisie(Initiales); // Pour chaque initiale, on l'associe � sa couleur
					verif=TestCouleur(Choix);//Pour chaque couleur, on v�rifie qu'elle existe
				}
				else
					System.out.println("Votre combinaison est trop longue ou trop courte, �crivez exactement 4 couleurs.");
					//Robustesse longueur de combinaison
			}
			
			return Choix;
		}
		
		public static boolean TestCouleur (String[] user)
		{//Ce sous-programme sert � la robustesse si l'utilisateur rentre un caract�re non compris dans le jeu
			boolean PasPr�sente=false;
			int CouleursNonPr�sentes;
			int i=0;
			while (i<4)
			{
				CouleursNonPr�sentes=0;
				for(int j=0;j<8;j++)
					if (!user[i].equals(Couleurs[j]))//On chaque couleur 
						CouleursNonPr�sentes+=1;
				if (CouleursNonPr�sentes==8) //si aucune couleur n'est pr�sente on renvoie un message
				{
					PasPr�sente=true;
					System.out.println("La couleur n�"+ (i+1) + " que vous avez demand� n'est pas comprise dans le jeu.");
				}

				i++;
			}
			return PasPr�sente;
		}
		
		public static int bienplac� (String[] proposition,String solution[])
		{//Compte le nombre de pions bien plac�s dans une proposition
			int bp=0;
			for (int i=0;i<4;i++)
					if(proposition[i].equals(solution[i]))/*Si la couleur est au m�me endroit dans la solution 
														   *que dans la proposition, alors le pion est bien plac�*/
						bp+=1;
			return bp;
		}

		public static int malplac� (String[] joueur, String[] solution)
		{//Compte le nombre de pions mal plac�s dans une proposition
			int mp=0;
			for(int j=0;j<4;j++)
				if(!joueur[j].equals(solution[j]))
					for(int k=0;k<4;k++)
						if (joueur[j].equals(solution[k]))/*Si le pion choisi n'est pas � la bonne place mais
						 								   *est pr�sent dans la solution, alors le compteur de 
						 								   *mal plac� augmente */
						{
							mp+=1;
							break;
						}
			return mp;
		}
		
		
		
		public static String[] remplirhistorique (String[] historique,String[][] propositions, String[] Choix, int n, int bp, int mp)
		{//Ce sous-programme sert � remplir l'historique des propositions de l'utilisateur
			for (int i=0;i<4;i++)
				propositions[n][i]=Choix[i];
			historique[n]=descriptionTableau(propositions[n])+" "+bp+" bien plac�(s), "+mp+" mal plac�(s)";
			//on remplit l'historique avec la proposition et le nombre de pions bien ou mal plac�s
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
		    int bienPlac�s=0;
		    int nombreDePropositions=0;
		    int nombreCouleurTrouv�e=0;
		    int i;  
		    /* trouver les bonnes couleurs */
		    for(i=1;i<=8;i++)
		    {
		    	//Pour chaque couleur on va v�rifier si elles sont pr�sentes dans la combinaison secr�te et combien de fois 
		    	proposition[0]=i;proposition[1]=i;proposition[2]=i;proposition[3]=i;
		        bienPlac�s=bienplac�(Couleural�atoire(proposition),solution);
		        nombreCouleurTrouv�e=nombreCouleurTrouv�e+bienPlac�s;
		        switch(bienPlac�s)
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
		        if(nombreCouleurTrouv�e==4)
		        	break;
		        }
		    	System.out.println("L'�tape pr�liminaire a pris " + nombreDePropositions +" tours.");
		    	BonnesCouleursStr=Couleural�atoire(bonnesCouleurs);//on attribue les couleurs aux chiffres trouv�s
		    	System.out.println(descriptionTableau(BonnesCouleursStr));
		    	return BonnesCouleursStr;
		}
		public static int[] tourDuJoueur(String[] proposition, Scanner reponse)        
	    /* ce sous-programme contient le d�roulement du tour du joueur, par soucis de lisibilit�, nous avons pr�f�r� faire un sous-programme d�di� plut�t que de copier/coller le code pour chaque version du mastermind invers� */
	    {
	    int bienPlac�s=0;
	    int malPlac�s=0;
	    int[] rep=new int[2];
	    System.out.println("Proposition : "+(descriptionTableau(proposition)));    /* � chaque proposition, l'ordinateur demande au joueur d'indiquer le nombre de pions bien plac�s et mal plac�s (ce fonctionnement sera repris dans les versions suivantes du programme) */
	     System.out.println("Entrez le nombre de pions bien plac�s.");
	     bienPlac�s=Integer.parseInt(reponse.nextLine());
	         
	     /* Robustesse */                                                                        /* le joueur ne doit pas pouvoir entrer un nombre de pions bien plac�s sup�rieur � 4 */
	     while(bienPlac�s>4)
	         {System.out.println("Erreur de saisie, veuillez entrer un nombre inf�rieur ou �gal � 4.");
	         System.out.println("Proposition : "+(descriptionTableau(proposition)));
	         System.out.println("Entrez le nombre de pions bien plac�s.");
	         bienPlac�s=Integer.parseInt(reponse.nextLine());}
	     /*-------------*/
	         
	     System.out.println("Entrez le nombre de pions mal plac�s.");
	     malPlac�s=Integer.parseInt(reponse.nextLine());
	     
	     /* Robustesse */                                                                        /* le joueur ne doit pas pouvoir entrer un nombre de pions mal plac�s sup�rieur � 4 */
	     while(malPlac�s>4)
	         {System.out.println("Erreur de saisie, veuillez entrer un nombre inf�rieur ou �gal � 4.");
	         System.out.println("Proposition : "+(descriptionTableau(proposition)));
	         System.out.println("Entrez le nombre de pions mal plac�s.");
	          malPlac�s=Integer.parseInt(reponse.nextLine());}
	     /*-------------*/        
	     
	     /* Robustesse */    
	     while(bienPlac�s+malPlac�s>4)                                                            /* le joueur ne doit pas pouvoir entrer un nombre de pions total (bien et mal plac�s) sup�rieur � 4 */
	         {System.out.println("Erreur de saisie, le nombre de pions bien et mal plac�s doit �tre inf�rieur ou �gal � 4.");
	         System.out.println("Proposition : "+(descriptionTableau(proposition)));    
	         System.out.println("Entrez le nombre de pions bien plac�s.");                        /* si c'est le cas, on lui redemande d'entrer le nombre de pions bien et mal plac�s */
	         bienPlac�s=Integer.parseInt(reponse.nextLine());
	         while(bienPlac�s>4)                                                                    /* en faisant attention qu'il ne puisse toujours pas entrer un nombre de pions bien OU mal plac�s sup�rieur � 4 */
	            {System.out.println("Erreur de saisie, veuillez entrer un nombre inf�rieur ou �gal � 4.");
	             System.out.println("Proposition : "+(descriptionTableau(proposition)));
	             System.out.println("Entrez le nombre de pions bien plac�s.");
	             bienPlac�s=Integer.parseInt(reponse.nextLine());}
	         System.out.println("Entrez le nombre de pions mal plac�s.");
	         malPlac�s=Integer.parseInt(reponse.nextLine());
	         while(malPlac�s>4)
	             {System.out.println("Erreur de saisie, veuillez entrer un nombre inf�rieur ou �gal � 4.");
	             System.out.println("Proposition : "+(descriptionTableau(proposition)));
	             System.out.println("Entrez le nombre de pions mal plac�s.");
	              malPlac�s=Integer.parseInt(reponse.nextLine());}}
	    /*-------------*/
	     
	    rep[0]=bienPlac�s;
	    rep[1]=malPlac�s;
	    return rep;
	    }
		public static String[] TrouvercouleursM3 (Scanner clavier)
		{/*Renvoie un tableau avec les couleurs de la solution � l'int�rieur, nuance avec le TrouverCouleurs :
		*ici c'est l'utilisteur qui indique le nombre de pions bien plac�s
		*/
			int[] proposition= new int[4];
		    int[] bonnesCouleurs= new int[0];
		    String[] BonnesCouleursStr=new String[4];
		    int bienPlac�s=0;
		    int[] BienetMalPlac�s= new int[2];
		    int nombreDePropositions=0;
		    int nombreCouleurTrouv�e=0;
		    int i;  
		    /* trouver les bonnes couleurs */
		    for(i=1;i<=8;i++)
		    {
		    	//Pour chaque couleur on va v�rifier si elles sont pr�sentes dans la combinaison secr�te et combien de fois 
		    	proposition[0]=i;proposition[1]=i;proposition[2]=i;proposition[3]=i;
		        BienetMalPlac�s=tourDuJoueur(Couleural�atoire(proposition),clavier);
		    	bienPlac�s=BienetMalPlac�s[0];
		        nombreCouleurTrouv�e=nombreCouleurTrouv�e+bienPlac�s;
		        switch(bienPlac�s)
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
		        if(nombreCouleurTrouv�e==4)
		        	break;
		        }
		    	System.out.println("L'�tape pr�liminaire a pris " + nombreDePropositions +" tours.");
		    	BonnesCouleursStr=Couleural�atoire(bonnesCouleurs);//on attribue les couleurs aux chiffres trouv�s
		    	System.out.println(descriptionTableau(BonnesCouleursStr));
		    	return BonnesCouleursStr;
		}
		
		
		
		
		public static int[] ajouterPremiereCase(int[] tab, int Case)    
	    /* ce sous-programme ajoute une case voulue au d�but d'un tableau */
	    {
	    int[] res=new int[tab.length+1];     /* on cr�� un tableau plus grand d'une case que celui en param�tre */
	    System.arraycopy(tab,0,res,1,tab.length);  /* on copie ensuite dedans le tableau en param�tre, 
	    											*� partir de la 2em case du tableau res*/
	    res[0]=Case;                /* enfin on ajoute la premiere case qui est donn�e en param�tre */
	    return res;
	    }
		
		
		public static void JeuM1 (String[] solution, int maxtour,Scanner clavier)
		{// c'est le sous programme qui r�git le jeu du Mode 1
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
				System.out.println("Tour n�" + (nbtours+1) +" sur " +(maxtour) );
				joueur=ChoixUser(clavier);
				verif =TestCouleur(joueur);
				while (verif)/*Si les initiales donn�es par le joueur ne sont pas comprises dans le jeu, 
								on lui demande de re saisir une combinaison*/
				{
					joueur=ChoixUser(clavier);
					verif =TestCouleur(joueur);
				}
				System.out.println("Vous avez choisi de tester la combinaison suivante : ");
				System.out.println(descriptionTableau(joueur));
				bp=bienplac�(joueur, solution);
				mp=malplac�(joueur, solution);
				if (bp>0)
					System.out.println("Il y a " + bp +" pions bien plac�(s).");
				if (mp>0)
					System.out.println("Il y a " + mp +" pions mal plac�(s).");
				if((bp==0)&&(mp==0))
					System.out.println("Aucune couleur propos�e n'est contenue dans la combinaison secr�te.");
				if(bp==4)
				{
					fin=true;
					System.out.println("F�licitations ! Vous avez r�ussi � trouver la bonne combinaison en "+nbtours+" tour(s).");
					System.out.println("La combinaison secr�te cr��e par l'ordi �tait :");
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
				System.out.println("Nombre de tours maximums d�pass�s ! Dommage !");
				System.out.println("La r�ponse �tait :");
				System.out.println(descriptionTableau(solution));
		}

		public static void JeuM2(String[] solution, Scanner clavier)
		{//Sous programme r�gissant le jeu du mode 2, 
			String[] propositionfinale= new String[4];
			int compteurtours=0;
			propositionfinale=Trouvercouleurs(solution);
			int bp=0;
			int prebp=0;
			String tmp;
			bp=bienplac�(propositionfinale,solution);
			System.out.println("Historique :");
			System.out.println(descriptionTableau(propositionfinale) +" "+bp+" bien plac�(s)" );
			compteurtours=1;
			prebp=bp;
			while (bp!=4)
				{
					for (int i=3;i>=1;i--)
					{/*ici la strat�gie consiste � parcourir le tableau � l'envers et � intervertir deux 
					*couleurs successives.Si le nombre de pions bien plac�s diminue,on revient � l'arrangement
					*de d�part la case d'avant.
					*/
							if (!propositionfinale[i].equals(propositionfinale[i-1]))
							{//On intervertit seulement les couleursqui ne sont pas les m�mes
								tmp=propositionfinale[i];
								propositionfinale[i]=propositionfinale[i-1];
								propositionfinale[i-1]=tmp;
								bp=bienplac�(propositionfinale,solution);
								System.out.println(descriptionTableau(propositionfinale)+ " " + bp +" bien plac�(s)");
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
							else //si les couleurs i et i-1 sont les m�mes
							{
								tmp=propositionfinale[i];
								if (i!=1)
								{//On �change avec la case d'apr�s
									propositionfinale[i]=propositionfinale[i-2];
									propositionfinale[i-2]=tmp;
								}
								else
								{//Si i=1, on intervertit avec la derni�re case du tableau
									propositionfinale[i]=propositionfinale[3];
									propositionfinale[3]=tmp;
								}
									
								bp=bienplac�(propositionfinale,solution);
								System.out.println(descriptionTableau(propositionfinale)+ " " + bp +" bien plac�(s)");
								if (bp==4)
									break;
								if (bp<prebp)
								{//si la combinaison pr�c�dente avait plus de biens plac�s, on y revient.
									tmp=propositionfinale[i];
									propositionfinale[i]=propositionfinale[i-1];
									propositionfinale[i-1]=tmp;		
								}
								prebp=bp;
								compteurtours+=1;
								}
							
							
					}
				}
				System.out.println("C'est fini ! L'ordinateur a trouv� votre combinaison secr�te en " + compteurtours + " tours :");
				System.out.println(descriptionTableau(propositionfinale));			
		}
		public static void JeuM3(Scanner clavier)
		{//Sous-programme r�gissant le sous-programme du mode 3
			String[] propositionfinale= new String[4];
			int compteurtours=0;
			propositionfinale=TrouvercouleursM3(clavier);
			int bp=tourDuJoueur(propositionfinale,clavier)[0];
			int prebp=0;
			String tmp;
			System.out.println("Historique :");
			System.out.println(descriptionTableau(propositionfinale) +" "+bp+" bien plac�(s)" );
			compteurtours=1;
			prebp=bp;
			while (bp!=4)
				{
					for (int i=3;i>=1;i--)
					{/*ici la strat�gie est la m�me que pour le mode 2, n�anmoins, ici c'est l'utikisateur qui d�clare				
					*les pions bien et mal plac�s 
					*/
							if (!propositionfinale[i].equals(propositionfinale[i-1]))
							{
							tmp=propositionfinale[i];
							propositionfinale[i]=propositionfinale[i-1];
							propositionfinale[i-1]=tmp;
							bp=tourDuJoueur(propositionfinale,clavier)[0];
							System.out.println(descriptionTableau(propositionfinale)+ " " + bp +" bien plac�(s)");
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
								System.out.println(descriptionTableau(propositionfinale)+ " " + bp +" bien plac�(s)");
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
				System.out.println("C'est fini ! L'ordinateur a trouv� votre combinaison secr�te en " + compteurtours + " tours :");
				System.out.println(descriptionTableau(propositionfinale));			
		}


		public static void Mode1 (Scanner clavier)
		{//On choisit ici le niveau avec lequel on souhaite jouer
			int maxtours=0;
			while (maxtours==0)	
			{
				System.out.println("4 niveaux disponibles : d�butant, interm�diaire et expert et libre");
				System.out.println("Entrez l'initiale de votre niveau :");	
				String niveau=clavier.nextLine();
				switch (niveau)
				{//chaque niveau correspond � un nombre de tours maximum
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
			String[] TirageOrdi= Couleural�atoire(tabini);
			JeuM1(TirageOrdi,maxtours,clavier);
		}

		public static void Mode2 (Scanner clavier)
		{//On entre la combinaison secr�te dans la console et on lance le programme pour que l'ordinateur la trouve
			System.out.println("Entrez votre combinaison secr�te : ");
			String[] solution=new String [4];
			solution=ChoixUser(clavier);
			System.out.println("Solution choisie");
			System.out.println(descriptionTableau(solution));
			JeuM2(solution,clavier);
		}
		
		public static void Mode3 (Scanner clavier)
		{//Ici, la solution est dans notre t�te, donc on lance sans mettre de solution en param�tre
			System.out.println("Pensez � une combinaison secr�te de 4 couleurs parmi celles-ci :");
			System.out.println(descriptionTableau(Couleurs));
			JeuM3(clavier);
		}
		

		public static int ChoixMode(Scanner clavier)
		{//sert � choisir le mode
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
					System.out.println("Vous n'avez pas rentr� 1,2 ou 3, veuillez r�essayer.");
			}
			return mode;
		}

		public static void main(String[] args) 
		{
			Scanner clavier = new Scanner(System.in);
			System.out.println("Bienvenue dans le jeu du MasterMind de Daugreilh Bastien et Gardi�s Samuel");
			System.out.println("Nous jouerons selon les r�gles de la consigne");
			System.out.println("Choisissez le mode de jeu que vous souhaitez");
			System.out.println("Tapez 1 si vous souhaitez trouver une combinaison choisie par l'ordinateur");
			System.out.println("Tapez 2 si vous souhaitez donner une combinaison que l'ordinateur doit trouver");
			System.out.println("Tapez 3 pour faire deviner � l'ordinateur votre combinaison secr�te");
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


