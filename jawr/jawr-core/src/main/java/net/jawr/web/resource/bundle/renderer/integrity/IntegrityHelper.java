package net.jawr.web.resource.bundle.renderer.integrity;

/**
 * Définition de l'algorithme permettant de générer une intégrité HASH pour les ressource web
 */
public interface IntegrityHelper {
	/**
	 * Génère une intégrité pour un contenu de type text: js, css.
	 * @param bundlePath
	 * @return
	 */
	public String generate(final String bundlePath);
}