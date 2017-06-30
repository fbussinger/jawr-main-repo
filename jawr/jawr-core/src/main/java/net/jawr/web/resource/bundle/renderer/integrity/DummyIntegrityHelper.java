package net.jawr.web.resource.bundle.renderer.integrity;

import net.jawr.web.util.StringUtils;

/**
 * Algorithme ne générant aucune intégrité
 */
public final class DummyIntegrityHelper implements IntegrityHelper {
  private DummyIntegrityHelper() {
  }

  public String generate(final String bundlePath) {
    return StringUtils.EMPTY;
  }

  public static IntegrityHelper getInstance() {
    return SingletonHolder.INSTANCE;
  }

  private static class SingletonHolder {
    private final static IntegrityHelper INSTANCE = new DummyIntegrityHelper();
  }
}
