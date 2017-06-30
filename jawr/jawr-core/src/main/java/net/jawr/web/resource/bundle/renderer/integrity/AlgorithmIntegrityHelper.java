package net.jawr.web.resource.bundle.renderer.integrity;

import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import net.jawr.web.exception.JawrLinkRenderingException;
import net.jawr.web.exception.ResourceNotFoundException;
import net.jawr.web.resource.bundle.handler.ResourceBundlesHandler;

/**
 * Algorithme générant une intégrité basée sur un algorithme
 */
public class AlgorithmIntegrityHelper implements IntegrityHelper {
  private final ResourceBundlesHandler bundler;
  private final Base64.Encoder base64Encoder = Base64.getEncoder();
  private final String integrityCompliantAlgo;

  public AlgorithmIntegrityHelper(ResourceBundlesHandler bundler) {
    this.bundler = bundler;
    this.integrityCompliantAlgo = org.apache.commons.lang3.StringUtils.remove(org.apache.commons.lang3.StringUtils.lowerCase(this.bundler.getConfig().getSRIConfig().getHashAlgorithm()), "-");
  }

  public String generate(final String bundlePath) {
    final StringWriter writer = new StringWriter();
    try {
      this.bundler.writeBundleTo(bundlePath, writer);
    } catch (ResourceNotFoundException e) {
      throw new JawrLinkRenderingException(e);
    }
    return this.generate(writer.toString().getBytes(this.bundler.getConfig().getResourceCharset()));
  }

  private String generate(byte[] content) {
    try {
      final MessageDigest messageDigest = MessageDigest.getInstance(this.bundler.getConfig().getSRIConfig().getHashAlgorithm());
      return this.integrityCompliantAlgo + "-" + this.base64Encoder.encodeToString(messageDigest.digest(content));
    } catch (NoSuchAlgorithmException e) {
      throw new JawrLinkRenderingException(e);
    }
  }
}
