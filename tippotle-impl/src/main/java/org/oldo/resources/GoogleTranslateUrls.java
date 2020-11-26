package org.oldo.resources;

import org.guppy4j.io.FileType;
import org.oldo.lang.Language;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static java.net.URLEncoder.encode;
import static org.guppy4j.io.FileTypeEnum.mp3;

/**
 * Constructs URLs for spoken word from Google Translate site,
 * with language-specific pronunciation
 */
public final class GoogleTranslateUrls implements UrlProvider {

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux i686; rv:21.0) "
            + "Gecko/20100101 Firefox/21.0 Iceweasel/21.0";

    private static final String BASE_URL = "http://translate.google.com/translate_tts?"
            + "ie=UTF-8&total=1&idx=0&client=odoepner-tippotle-" + System.currentTimeMillis();

    @Override
    public URL url(String s, Language language) throws MalformedURLException {
        return new URL(BASE_URL
                + (language != null ? "&tl=" + urlEncode(language.code()) : "")
                + "&q=" + urlEncode(s.toLowerCase()));
    }

    @Override
    public void prepareConnection(URLConnection c) {
        c.setRequestProperty("User-Agent", USER_AGENT);
    }

    @Override
    public FileType fileType() {
        return mp3;
    }

    private static String urlEncode(String text) {
        try {
            return encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
