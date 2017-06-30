package net.jawr.web.resource.bundle.renderer.integrity;

public interface ResourceIntegrityProvider {
  public String getResourceHash(final String bundlePath);
}
