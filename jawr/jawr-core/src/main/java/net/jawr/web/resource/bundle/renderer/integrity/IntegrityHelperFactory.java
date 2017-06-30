package net.jawr.web.resource.bundle.renderer.integrity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jawr.web.config.JawrConfig;
import net.jawr.web.resource.bundle.handler.ResourceBundlesHandler;

public final class IntegrityHelperFactory {
  /** The logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(IntegrityHelperFactory.class);

  private IntegrityHelperFactory() {

  }

  public static IntegrityHelper create(final ResourceBundlesHandler bundler) {
    final JawrConfig config = bundler.getConfig();
    if (config.getSRIConfig().isEnabled()) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Création d'un gestionnaire d'intégrité de ressource utilisant un algorithme " + config.getSRIConfig().getHashAlgorithm());
      }
      // je suis obligé de créer une nouvelle à chaque fois pour éviter d'éventuel pépin de multithread.
      return new AlgorithmIntegrityHelper(bundler);
    } else {
      return DummyIntegrityHelper.getInstance();
    }
  }
}
