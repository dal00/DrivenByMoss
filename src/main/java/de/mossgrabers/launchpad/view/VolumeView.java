// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.launchpad.view;

import de.mossgrabers.framework.ButtonEvent;
import de.mossgrabers.framework.Model;
import de.mossgrabers.framework.controller.color.ColorManager;
import de.mossgrabers.framework.daw.AbstractTrackBankProxy;
import de.mossgrabers.framework.daw.BitwigColors;
import de.mossgrabers.framework.daw.MasterTrackProxy;
import de.mossgrabers.framework.daw.data.TrackData;
import de.mossgrabers.framework.midi.MidiOutput;
import de.mossgrabers.launchpad.controller.LaunchpadColors;
import de.mossgrabers.launchpad.controller.LaunchpadControlSurface;


/**
 * 8 volume faders.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class VolumeView extends AbstractFaderView
{
    /**
     * Constructor.
     *
     * @param surface The surface
     * @param model The model
     */
    public VolumeView (final LaunchpadControlSurface surface, final Model model)
    {
        super (surface, model);
    }


    /** {@inheritDoc} */
    @Override
    public void onValueKnob (final int index, final int value)
    {
        this.model.getCurrentTrackBank ().setVolume (index, value);
    }


    /** {@inheritDoc} */
    @Override
    public void drawGrid ()
    {
        final ColorManager cm = this.model.getColorManager ();
        final AbstractTrackBankProxy tb = this.model.getCurrentTrackBank ();
        final MidiOutput output = this.surface.getOutput ();
        for (int i = 0; i < 8; i++)
        {
            final TrackData track = tb.getTrack (i);
            final int color = cm.getColor (BitwigColors.getColorIndex (track.getColor ()));
            if (this.trackColors[i] != color || !track.doesExist ())
                this.setupFader (i);
            this.trackColors[i] = color;
            output.sendCC (LaunchpadControlSurface.LAUNCHPAD_FADER_1 + i, track.getVolume ());
        }
    }


    /** {@inheritDoc} */
    @Override
    public void updateSceneButtons ()
    {
        final ColorManager cm = this.model.getColorManager ();
        final MasterTrackProxy track = this.model.getMasterTrack ();
        final int sceneMax = 9 * track.getVolume () / this.model.getValueChanger ().getUpperBound ();
        for (int i = 0; i < 8; i++)
        {
            final int color = cm.getColor (BitwigColors.getColorIndex (track.getColor ()));
            this.surface.setButton (LaunchpadControlSurface.LAUNCHPAD_BUTTON_SCENE8 + 10 * i, i < sceneMax ? color : LaunchpadColors.LAUNCHPAD_COLOR_BLACK);
        }
    }


    /** {@inheritDoc} */
    @Override
    public void onScene (final int scene, final ButtonEvent event)
    {
        if (event != ButtonEvent.DOWN)
            return;
        final MasterTrackProxy track = this.model.getMasterTrack ();
        track.setVolume (Math.min (127, (7 - scene) * this.model.getValueChanger ().getUpperBound () / 7));
    }
}