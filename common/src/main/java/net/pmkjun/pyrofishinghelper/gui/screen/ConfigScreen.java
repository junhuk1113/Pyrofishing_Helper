package net.pmkjun.pyrofishinghelper.gui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.pmkjun.pyrofishinghelper.FishHelperClient;

public class ConfigScreen extends Screen{
    Screen parentScreen;
    MinecraftClient mc;
    FishHelperClient client;
    private final int width;
    private final int height;
    private ButtonWidget toggleCustomTextureButton;

    public ConfigScreen(Screen parentScreen){
        super(Text.translatable("fishhelper.config.title"));
        this.parentScreen = parentScreen;
        this.mc = MinecraftClient.getInstance();
        this.client = FishHelperClient.getInstance();

        width = 160;
        height = 74;
    }

    protected void init(){
        super.init();
        String toggleTexture;

        if(client.data.toggleCustomTexture){
            toggleTexture = "fishhelper.config.customtexture_enable";
        }
        else{
            toggleTexture = "fishhelper.config.customtexture_disable";
        }


        // translatable 키로 변경 필요
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("fishhelper.config.totemtimer_setting"), btn -> {
            mc.setScreen(new TotemTimerConfigScreen(mc.currentScreen));
        }).dimensions(getRegularX() + 5, getRegularY() + 5, 150, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("fishhelper.config.fishcounter_setting"), btn -> {
            mc.setScreen(new FishCounterConfigScreen(mc.currentScreen));
        }).dimensions(getRegularX() + 5, getRegularY() + 5 + 20 + 2, 150, 20).build());

        toggleCustomTextureButton = ButtonWidget.builder(Text.translatable(toggleTexture), btn -> {
            onCustomTexturePress();
        }).dimensions(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 2, 150, 20).build();
        addDrawableChild(toggleCustomTextureButton);

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("fishhelper.config.backbutton"),button -> {
            mc.setScreen(parentScreen);
        }).dimensions(mc.getWindow().getScaledWidth() / 2 - 25 ,super.height-30, 50,20).build());
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
    }

    int getRegularX() {
        return  mc.getWindow().getScaledWidth() / 2 - width / 2;
    }

    int getRegularY() {
        return mc.getWindow().getScaledHeight() / 2 - height / 2;
    }

    private void onCustomTexturePress(){
        if(client.data.toggleCustomTexture){
            toggleCustomTextureButton.setMessage(Text.translatable("fishhelper.config.customtexture_disable"));
            client.data.toggleCustomTexture = false;
        }
        else{
            toggleCustomTextureButton.setMessage(Text.translatable("fishhelper.config.customtexture_enable"));
            client.data.toggleCustomTexture = true;
        }
        client.configManage.save();
    }
}
