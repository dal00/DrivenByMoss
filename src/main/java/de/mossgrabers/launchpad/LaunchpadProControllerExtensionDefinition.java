// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.launchpad;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.ControllerHost;

import java.util.UUID;


/**
 * Definition class for the Launchpad 1 extension.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class LaunchpadProControllerExtensionDefinition extends LaunchpadControllerExtensionDefinition
{
    private static final UUID EXTENSION_ID = UUID.fromString ("80B63970-64F1-11E5-A837-0800200C9A66");


    /** {@inheritDoc} */
    @Override
    public String getHardwareModel ()
    {
        return "Launchpad Pro";
    }


    /** {@inheritDoc} */
    @Override
    public UUID getId ()
    {
        return EXTENSION_ID;
    }


    /** {@inheritDoc} */
    @Override
    public void listAutoDetectionMidiPortNames (final AutoDetectionMidiPortNamesList list, final PlatformType platformType)
    {
        switch (platformType)
        {
            case WINDOWS:
                this.addDeviceDiscoveryPair ("MIDIIN2 (Launchpad Pro)", "MIDIOUT2 (Launchpad Pro)", list);
                break;

            case LINUX:
                this.addDeviceDiscoveryPair ("Launchpad Pro MIDI 2", "Launchpad Pro MIDI 2", list);
                break;

            case MAC:
                this.addDeviceDiscoveryPair ("Launchpad Pro Standalone Port", "Launchpad Pro Standalone Port", list);
                break;
        }
    }


    /** {@inheritDoc} */
    @Override
    public ControllerExtension createInstance (final ControllerHost host)
    {
        return new LaunchpadControllerExtension (this, host, true);
    }
}
