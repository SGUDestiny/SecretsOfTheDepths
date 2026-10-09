package destiny.secretsofthevoid.client.render;

import destiny.secretsofthevoid.client.models.PearlGearModel;
import destiny.secretsofthevoid.server.items.diving_gear.PearlGearItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PearlGearRenderer extends GeoArmorRenderer<PearlGearItem> {
    public PearlGearRenderer() {super(new PearlGearModel());}
}
