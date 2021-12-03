package src.fr.istic.prg1.tree;

import java.util.Scanner;



import fr.istic.prg1.tree_util.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;
import fr.istic.prg1.tree_util.NodeType;

/**
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 5.0
 * @since 2016-04-20
 * 
 *        Classe décrivant les images en noir et blanc de 256 sur 256 pixels
 *        sous forme d'arbres binaires.
 * 
 */

public class Image extends AbstractImage {
	private static final Scanner standardInput = new Scanner(System.in);

	public Image() {
		super();
	}

	public static void closeAll() {
		standardInput.close();
	}

	/**
	 * @param x abscisse du point
	 * @param y ordonnée du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumé dans this, false sinon
	 */
	@Override
	public boolean isPixelOn(int x, int y) {
		int niveau = 0;
		int longeur = 256;
		int hauteur = longeur / 2;
		int cptX = 0;
		int cptY = 0;
		Iterator<Node> it = this.iterator();
		while (it.nodeType() != NodeType.LEAF) {
			hauteur = longeur;
			if (niveau % 2 == 0) {
				if (y < hauteur + cptY) {
					it.goLeft();

				} else {
					it.goRight();
					cptY += hauteur;
				}
			} else {
				if (x < longeur + cptX) {
					it.goLeft();

				} else {
					it.goRight();
					cptX += longeur;
				}

			}
			niveau++;

		}
		return it.getValue().state == 1;
	}

	/**
	 * this devient identique à image2.
	 *
	 * @param image2 image à copier
	 *
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void affect(AbstractImage image2) {
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it1.clear();
		this.auxAffect(it1, it2);
	}

	public void auxAffect(Iterator<Node> it1, Iterator<Node> it2) {
		it1.addValue(it2.getValue());
		if (it2.nodeType() != NodeType.LEAF) {
			it1.goLeft();
			it2.goLeft();
			this.auxAffect(it1, it2);
			it1.goUp();
			it2.goUp();
			it1.goRight();
			it2.goRight();
			this.auxAffect(it1, it2);
			it1.goUp();
			it2.goUp();
		}

	}

	/**
	 * this devient rotation de image2 à 180 degrés.
	 *
	 * @param image2 image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate180(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		this.auxRotate180(it, it2);
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	public void auxRotate180(Iterator<Node> it1, Iterator<Node> it2) {
		it1.addValue(Node.valueOf(it2.getValue().state));
		it1.goRight();
		it2.goLeft();
		this.auxRotate180(it1, it2);
		it1.goUp();
		it2.goUp();
		it1.goLeft();
		it2.goRight();

	}

	/**
	 * this devient rotation de image2 à 90 degrés dans le sens des aiguilles
	 * d'une montre.
	 *
	 * @param image2 image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate90(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction non demeand�e");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient inverse vidéo de this, pixel par pixel.
	 *
	 * @pre !image.isEmpty()
	 */
	@Override
	public void videoInverse() {
		System.out.println();
		System.out.println("-------------------------------------------------");
		Iterator<Node> it = this.iterator();
		it.clear();
		auxInverse(it);
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	public void auxInverse(Iterator<Node> it) {
		it.addValue(Node.valueOf(1 - it.getValue().state));
	}

	/**
	 * this devient image miroir verticale de image2.
	 *
	 * @param image2 image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorV(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		Iterator<Node> it = this.iterator();
		this.auxMirrorV(it, image2.iterator(), 0);
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	public void auxMirrorV(Iterator<Node> it, Iterator<Node> it1, int i) {
		it.addValue(Node.valueOf(it1.getValue().state));
		if (it1.nodeType() != NodeType.LEAF) {
			it1.goLeft();
			if (i % 2 == 1) {
				it.goLeft();
			} else {
				it.goRight();
			}
			this.auxMirrorV(it, it1, i++);
			it.goUp();
			it1.goUp();
			i--;
			it1.goRight();
			if (i % 2 == 1) {
				it.goRight();
			} else {
				it.goLeft();
			}
			this.auxMirrorV(it, it1, i++);
			it.goUp();
			it1.goUp();
			i--;

		}

	}

	/**
	 * this devient image miroir horizontale de image2.
	 *
	 * @param image2 image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorH(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		Iterator<Node> it = this.iterator();
		auxMirrorH(it, image2.iterator(), 0);
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	public void auxMirrorH(Iterator<Node> it, Iterator<Node> it1, int i) {
		it.addValue(Node.valueOf(it1.getValue().state));
		if (it1.nodeType() != NodeType.LEAF) {
			it1.goLeft();
			if (i % 2 == 1) {
				it.goRight();
			} else {
				it.goLeft();
			}
			this.auxMirrorV(it, it1, i++);
			it.goUp();
			it1.goUp();
			i--;
			it1.goRight();
			if (i % 2 == 1) {
				it.goLeft();
			} else {
				it.goRight();
			}
			this.auxMirrorV(it, it1, i++);
			it.goUp();
			it1.goUp();
			i--;

		}

	}

	/**
	 * this devient quart supérieur gauche de image2.
	 *
	 * @param image2 image à agrandir
	 * 
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomIn(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		this.auxZoomIn(this.iterator(), image2.iterator(), 0);
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	public void auxZoomIn(Iterator<Node> it, Iterator<Node> it1, int i) {
		if (i < 2) {
			if (it1.nodeType() != NodeType.LEAF) {
				it1.goLeft();
				i++;
				this.auxZoomIn(it, it1, i);
			} else {
				this.auxAffect(it, it1);
			}
		} else {
			this.auxAffect(it, it1);
		}
	}

	/**
	 * Le quart supérieur gauche de this devient image2, le reste de this devient
	 * éteint.
	 * 
	 * @param image2 image à réduire
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomOut(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		it.clear();
		it.goRight();
		it.addValue(Node.valueOf(0)); // Eteint la partie inferieur
		it.goRoot();
		it.goLeft();
		it.addValue(Node.valueOf(2));
		it.goRight();
		it.addValue(Node.valueOf(0)); // eteint la partie superieur a droite
		it.goUp();
		it.goLeft();
		this.auxAffect(it, image2.iterator());

	}

	/**
	 * this devient l'intersection de image1 et image2 au sens des pixels allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void intersection(AbstractImage image1, AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		Iterator<Node>it = this.iterator();
		this.auxIntersection(it, image1.iterator(), image2.iterator());
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	public void auxIntersection(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {

		if (it1.getValue().state == 0 || it2.getValue().state == 0) {
			it.addValue(Node.valueOf(0));
		} else if(it1.getValue().state == it2.getValue().state) {
			switch(it1.getValue().state) {
			case 1:
				it.addValue(Node.valueOf(1));
				break;
			case 2: 
				it.addValue(Node.valueOf(2));
				it.goLeft();
				it1.goLeft();
				it2.goLeft();
				this.auxIntersection(it, it1, it2);
				it.goUp();
				it1.goUp();
				it2.goUp();
				it.goRight();
				it1.goRight();
				it2.goRight();
				this.auxIntersection(it, it1, it2);
				it.goUp();
				it1.goUp();
				it2.goUp();

			}
				
			
		}
	}

	/**
	 * this devient l'union de image1 et image2 au sens des pixels allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void union(AbstractImage image1, AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		Iterator<Node> it = this.iterator();
		it.clear();
		this.auxUnion(it, image1.iterator(), image2.iterator());
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	public void auxUnion(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {
		int e;
		e = it2.getValue().state + it1.getValue().state;
		switch (e) {
		case 2:
			if (it1.getValue().state == 0) {
				it.addValue(it2.getValue());
			} else if (it2.getValue().state == 0) {
				it.addValue(it1.getValue());
			} else {
				it.addValue(Node.valueOf(1));
			}
			break;
		case 3:
			it.addValue(Node.valueOf(1));
		case 4:
			it.addValue(Node.valueOf(2));
			it.goLeft();
			it1.goLeft();
			it2.goLeft();
			this.auxUnion(it, it1, it2);
			it.goUp();
			it1.goUp();
			it2.goUp();
			it.goRight();
			it1.goRight();
			it2.goRight();
			this.auxUnion(it, it1, it2);
			it.goUp();
			it1.goUp();
			it2.goUp();

		default:
			it.addValue(Node.valueOf(e));
			break;
		}

	}

	/**
	 * Attention : cette fonction ne doit pas utiliser la commande isPixelOn
	 * 
	 * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255)
	 *         sont allumés dans this, false sinon
	 */
	@Override
	public boolean testDiagonal() {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
		return false;
	}
	public boolean auxDiagonal () {
		return false ;
	}

	/**
	 * @param x1 abscisse du premier point
	 * @param y1 ordonnée du premier point
	 * @param x2 abscisse du deuxième point
	 * @param y2 ordonnée du deuxième point
	 * @pre !this.isEmpty()
	 * @return true si les deux points (x1, y1) et (x2, y2) sont représentés par
	 *         la même feuille de this, false sinon
	 */
	@Override
	public boolean sameLeaf(int x1, int y1, int x2, int y2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
		return false;
	}

	/**
	 * @param image2 autre image
	 * @pre !this.isEmpty() && !image2.isEmpty()
	 * @return true si this est incluse dans image2 au sens des pixels allumés
	 *         false sinon
	 */
	@Override
	public boolean isIncludedIn(AbstractImage image2) {
		
		boolean in = auxInclude(this.iterator(), image2.iterator());
		
		return in;
	}
	public boolean auxInclude (Iterator<Node>it, Iterator<Node>it1) {
		
			boolean include = false ;
		 if( it.getValue().state != 1 && it1.getValue().state != 0) {
			 boolean includeLeft = false;
			 boolean includeRight = false ;
			it.goLeft();
			it1.goLeft();
			if(it1.getValue().state == 1) {
				return includeLeft = true ;
			} else if (it.getValue().state == 0) {
				return includeLeft = true ;
			} else {
				auxInclude(it, it1);
			}
			it.goUp();
			it1.goUp();
			it.goRight();
			it1.goRight();
			
			if(it1.getValue().state == 1) {
				return includeRight = true ;
			} else if (it.getValue().state == 0) {
				return includeRight = true ;
			} else {
				auxInclude(it, it1);
			}
			it.goUp();
			it1.goUp();
			
			include =  includeLeft&&includeRight;
			
		}
		return include; 
	}

}
