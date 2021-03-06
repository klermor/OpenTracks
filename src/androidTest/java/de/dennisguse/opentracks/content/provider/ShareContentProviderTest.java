package de.dennisguse.opentracks.content.provider;

import android.net.Uri;
import android.util.Pair;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import de.dennisguse.opentracks.content.data.Track;
import de.dennisguse.opentracks.io.file.TrackFileFormat;

public class ShareContentProviderTest {

    @Test
    public void testCreateandParseURI_invalid() {
        Uri uri = Uri.parse("content://de.dennisguse.opentracks.debug.content/tracks/1");

        Assert.assertEquals(0, ShareContentProvider.parseURI(uri).size());
    }

    @Test
    public void testCreateandParseURI_valid() {
        Set<Track.Id> trackIds = new HashSet<>();
        trackIds.add(new Track.Id(1));
        trackIds.add(new Track.Id(3));
        trackIds.add(new Track.Id(5));

        Pair<Uri, String> shareURIandMIME = ShareContentProvider.createURI(trackIds, "TrackName", TrackFileFormat.KML_ONLY_TRACK);

        Assert.assertEquals(trackIds, ShareContentProvider.parseURI(shareURIandMIME.first));
    }

    @Test
    public void testCreateURIescapeFilename() {
        Set<Track.Id> trackIds = new HashSet<>();
        trackIds.add(new Track.Id(1));
        Pair<Uri, String> shareURIandMIME = ShareContentProvider.createURI(trackIds, "../../&1=1", TrackFileFormat.KML_ONLY_TRACK);

        Assert.assertEquals(Uri.parse("content://de.dennisguse.opentracks.debug.content/tracks/kml_only_track/1/..%2F..%2F%261%3D1.kml"), shareURIandMIME.first);
    }
}