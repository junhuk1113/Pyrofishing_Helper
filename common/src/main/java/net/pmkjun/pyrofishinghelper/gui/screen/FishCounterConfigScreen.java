package net.pmkjun.pyrofishinghelper.gui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import net.pmkjun.pyrofishinghelper.gui.widget.Slider;

public class FishCounterConfigScreen extends Screen{
    private MinecraftClient mc;
    private FishHelperClient client;
    private final Screen parentScreen;
    private ButtonWidget toggleFishCounterButton;
    private ButtonWidget toggleGradeProbabilityButton;
    private ButtonWidget toggleEarningCalculatorButton;

    private Slider counterXSlider;
    private Slider counterYSlider;

    private final int width;
    private final int height;

    String toggleFishCounter, toggleGradeProbability, toggleEarningCalculator, resetCounter;

    public FishCounterConfigScreen(Screen parentScreen) {
        super(Text.translatable("fishhelper.config.title"));
        this.parentScreen = parentScreen;
        this.mc = MinecraftClient.getInstance();
        this.client = FishHelperClient.getInstance();

        width = 160;
        height = 152;//140+12
    }

    @Override
    protected void init() {
        super.init();
        initButtonkey();

        toggleFishCounterButton = ButtonWidget.builder(Text.translatable(toggleFishCounter),button -> {
            toggleFishCounter();
        }).dimensions(getRegularX() + 5, getRegularY(), 150, 20).build();
        this.addDrawableChild(toggleFishCounterButton);

        toggleGradeProbabilityButton = ButtonWidget.builder(Text.translatable(toggleGradeProbability),button -> {
            toggleGradeProbability();
        }).dimensions(getRegularX() + 5, getRegularY()+(20+2), 150, 20).build();
        this.addDrawableChild(toggleGradeProbabilityButton);

        toggleEarningCalculatorButton = ButtonWidget.builder(Text.translatable(toggleEarningCalculator), button ->{
            toggleEarningCalculator();
        }).dimensions(getRegularX()+5,getRegularY()+(20+2)*2,150,20).build();
        this.addDrawableChild(toggleEarningCalculatorButton);

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("fishhelper.config.fishcounter_reset"), button ->{
            client.resetFishCounter();
        }).dimensions(getRegularX()+5, getRegularY()+(20+2)*3, 150, 20).build());

        counterXSlider = new Slider(getRegularX() + 5, getRegularY()+(20+2)*5, 150, 20, Text.literal("X : "),1,1000,this.client.data.Counter_xpos){
            @Override
            protected void applyValue() {
                client.data.Counter_xpos = getValueInt();
                client.configManage.save();
            }
        };
        this.addDrawableChild(counterXSlider);

        counterYSlider = new Slider(getRegularX() + 5, getRegularY()+(20+2)*6, 150, 20, Text.literal("Y : "),1,1000,this.client.data.Counter_xpos){
            @Override
            protected void applyValue() {
                client.data.Counter_ypos = getValueInt();
                client.configManage.save();
            }
        };
        this.addDrawableChild(counterYSlider);

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("fishhelper.config.backbutton"),button -> {
            mc.setScreen(parentScreen);
        }).dimensions(mc.getWindow().getScaledWidth() / 2 - 25 ,super.height-30, 50,20).build());
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawTextWithShadow(this.textRenderer, Text.translatable("fishhelper.config.changepos"), getRegularX() + 5, getRegularY()+(20 + 2)*5-10, 0xFFFFFF);

        this.counterXSlider.render(context, mouseX, mouseY, delta);
        this.counterYSlider.render(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);
    }

    //버튼 눌렀을 때 동작
    private void toggleFishCounter(){
        if(client.data.toggleFishCounter){
            toggleFishCounterButton.setMessage(Text.translatable("fishhelper.config.fishcounter_disable"));
            client.data.toggleFishCounter = false;
        }
        else{
            toggleFishCounterButton.setMessage(Text.translatable("fishhelper.config.fishcounter_enable"));
            client.data.toggleFishCounter = true;
        }
        client.configManage.save();
    }
    
    private void toggleGradeProbability(){
        if(client.data.toggleGradeProbability){
            toggleGradeProbabilityButton.setMessage(Text.translatable("fishhelper.config.fishcounter_setting.togglegradeprobability_disable"));
            client.data.toggleGradeProbability = false;
        }
        else{
            toggleGradeProbabilityButton.setMessage(Text.translatable("fishhelper.config.fishcounter_setting.togglegradeprobability_enable"));
            client.data.toggleGradeProbability = true;
        }
        client.configManage.save();
    }

    private void toggleEarningCalculator(){
        if(client.data.toggleEarningCalculator){
            toggleEarningCalculatorButton.setMessage(Text.translatable("fishhelper.config.fishcounter_setting.toggleEarningCalculator_disable"));
            client.data.toggleEarningCalculator = false;
        }
        else{
            toggleEarningCalculatorButton.setMessage(Text.translatable("fishhelper.config.fishcounter_setting.toggleEarningCalculator_enable"));
            client.data.toggleEarningCalculator = true;
        }
        client.configManage.save();
    }

    void initButtonkey(){
        if(client.data.toggleFishCounter){
            toggleFishCounter = "fishhelper.config.fishcounter_enable";
        }
        else {
            toggleFishCounter = "fishhelper.config.fishcounter_disable";
        }

        if(client.data.toggleGradeProbability){
            toggleGradeProbability = "fishhelper.config.fishcounter_setting.togglegradeprobability_enable";
        }
        else{
            toggleGradeProbability = "fishhelper.config.fishcounter_setting.togglegradeprobability_disable";
        }

        if(client.data.toggleEarningCalculator){
            toggleEarningCalculator = "fishhelper.config.fishcounter_setting.toggleEarningCalculator_enable";
        }
        else{
            toggleEarningCalculator = "fishhelper.config.fishcounter_setting.toggleEarningCalculator_disable";
        }
    }

    int getRegularX() {
        return  mc.getWindow().getScaledWidth() / 2 - width / 2;
    }

    int getRegularY() {
        return mc.getWindow().getScaledHeight() / 2 - height / 2;
    }
}
