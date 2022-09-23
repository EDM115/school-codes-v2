/**
* Crée une fiche de salaire à partir du salaire brut
* @author L.Lederrey
*/

class Salaire {
	void principal() {
		float pam = 0.75F;
		float pavd = 0.1F;
		float pcsg = 7.5F;
		float pcrds = 0.5F;
		float pavp = 6.75F;
		float pc = 2.4F;
		float assiette = 1.75F;
		float brut = SimpleInput.getFloat("Entrez votre salaire brut : ");
		
		float am = (brut * pam) / 100;
		float avd = (brut * pavd) / 100;
		float csg = (((brut - ((brut * assiette) / 100)) * pcsg) / 100);
		float crds = (((brut - ((brut * assiette) / 100)) * pcrds) / 100);
		float avp = (brut * pavp) / 100;
		float c = (brut * pc) / 100;
		float total = am + avd + csg + crds + avp + c;
		float net = brut - total;
		
		System.out.println("Salaire brut : \t\t\t\t" + brut);
		System.out.println("Assurance maladie : \t\t\t" + am);
		System.out.println("Assurance vieillesse déplafonnée : \t" + avd);
		System.out.println("Contribution sociale généralisée : \t" + csg);
		System.out.println("CRDS : \t\t\t\t\t" + crds);
		System.out.println("Assurance vieillesse plafonnée : \t" + avp);
		System.out.println("Chômage : \t\t\t\t" + c);
		System.out.println("Prélèvements totaux : \t\t\t" + total);
		System.out.println("Salaire net : \t\t\t\t" + net);
	}
}
