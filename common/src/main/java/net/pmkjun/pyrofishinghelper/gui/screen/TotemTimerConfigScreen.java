package net.pmkjun.pyrofishinghelper.gui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import net.pmkjun.pyrofishinghelper.gui.widget.Slider;
import net.pmkjun.pyrofishinghelper.util.ConvertActivateTime;
import net.pmkjun.pyrofishinghelper.util.ConvertCooldown;

public class TotemTimerConfigScreen extends Screen {

    private MinecraftClient mc;
    private FishHelperClient client;
    private final Screen parentScreen;
    private TextFieldWidget CooldownReduction_TextField;

    private ButtonWidget toggleTotemButton;

    private Slider activateTimeSlider;
    private Slider cooldownSlider;

    private Slider timerXSlider;
    private Slider timerYSlider;

    private final int width;
    private final int height;

    public TotemTimerConfigScreen(Screen parentScreen){
        super(Text.translatable("fishhelper.config.title"));
        this.parentScreen = parentScreen;
        this.mc = MinecraftClient.getInstance();
        this.client = FishHelperClient.getInstance();

        width = 160;
        height = 132;
    }

    @Override
    protected void init() {
        String toggleTotem;

        if(client.data.toggleTotemtime){
            toggleTotem = "fishhelper.config.enable";
        }
        else{
            toggleTotem = "fishhelper.config.disable";
        }

        activateTimeSlider = new Slider(getRegularX() + 5, getRegularY(), 150, 20, Text.literal(""),0,20,ConvertActivateTime.asLevel(this.client.data.valueTotemActivetime)){
            @Override
            protected void updateMessage() {
                int level = getValueInt();
                this.setMessage(Text.literal(Text.translatable("fishhelper.config.activatefield").getString()
                        +" : "+ level +"lv ("+
                        ConvertActivateTime.asMinute(level)+
                        Text.translatable("fishhelper.config.minute").getString()+")"));
            }
        };
        cooldownSlider = new Slider(getRegularX() + 5, getRegularY() + (20 + 2), 150, 20, Text.literal(""),0,10,ConvertCooldown.asLevel(this.client.data.valueTotemCooldown)){
            @Override
            protected void updateMessage() {
                int level = getValueInt();
                this.setMessage(Text.literal(Text.translatable("fishhelper.config.cooldownfield").getString()
                        +" : "+ level +"lv ("+ ConvertCooldown.asMinute(level)+
                        Text.translatable("fishhelper.config.minute").getString()+")"));
            }
        };

        this.addDrawableChild(activateTimeSlider);
        this.addDrawableChild(cooldownSlider);

        //토템 쿨감시간
        this.CooldownReduction_TextField = new TextFieldWidget(this.textRenderer,getRegularX() + 5 + 90,getRegularY()+(20 + 2)*2,35,10,this.CooldownReduction_TextField,Text.translatable("fishhelper.config.cooldownreductionfield"));
        this.CooldownReduction_TextField.setText(Double.toString(this.client.data.valueCooldownReduction/(double)1000));
        this.addSelectableChild(this.CooldownReduction_TextField);

        toggleTotemButton = ButtonWidget.builder(Text.translatable(toggleTotem),button -> {
            toggleTotemtime();
        }).dimensions(getRegularX() + 5,getRegularY()+(20 + 2)*2+(10+4), 150,20).build();
        this.addDrawableChild(toggleTotemButton);

        timerXSlider = new Slider(getRegularX() + 5,getRegularY()+(20 + 2)*4+(10+2),150,20,Text.literal("X : "),1,1000,this.client.data.Timer_xpos){
            @Override
            protected void applyValue() {
                client.data.Timer_xpos = getValueInt();
                client.configManage.save();
            }
        };
        this.addDrawableChild(timerXSlider);
        timerYSlider = new Slider(getRegularX() + 5,getRegularY()+(20 + 2)*5+(10+2),150,20,Text.literal("Y : "),1,1000,this.client.data.Timer_ypos){
            @Override
            protected void applyValue() {
                client.data.Timer_ypos = getValueInt();
                client.configManage.save();
            }
        };
        this.addDrawableChild(timerYSlider);

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("fishhelper.config.backbutton"),button -> {
            mc.setScreen(parentScreen);
        }).dimensions(10,super.height-30, 50,20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("fishhelper.config.savebutton"),button -> {
            changeSetting();
            mc.setScreen(parentScreen);
        }).dimensions(super.width-60,super.height-30, 50,20).build());
    }
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers)
                || this.CooldownReduction_TextField.keyPressed(keyCode, scanCode, modifiers);
    }
    @Override
    public boolean charTyped(char chr, int keyCode) {
        return this.CooldownReduction_TextField.charTyped(chr, keyCode);
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawTextWithShadow(this.textRenderer, Text.translatable("fishhelper.config.cooldownreductionfield"), getRegularX() + 5, getRegularY()+(20 + 2)*2, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer,Text.translatable("fishhelper.config.changepos"),getRegularX() + 5,getRegularY()+(20 + 2)*4,0xFFFFFF);

        this.activateTimeSlider.render(context, mouseX, mouseY, delta);
        this.cooldownSlider.render(context, mouseX, mouseY, delta);

        this.timerXSlider.render(context, mouseX, mouseY, delta);
        this.timerYSlider.render(context, mouseX, mouseY, delta);

        this.CooldownReduction_TextField.render(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);
    }
    private void changeSetting(){
        try{
            client.data.valueTotemActivetime = ConvertActivateTime.asMinute(activateTimeSlider.getValueInt());
            client.data.valueTotemCooldown = ConvertCooldown.asMinute(cooldownSlider.getValueInt());
            client.data.valueCooldownReduction = (long)(Double.parseDouble(CooldownReduction_TextField.getText())*1000);

            client.configManage.save();
        }
        catch (NumberFormatException e){
            System.out.println("NumberFormatException!");
        }

    }

    private void toggleTotemtime(){
        if(client.data.toggleTotemtime){
            toggleTotemButton.setMessage(Text.translatable("fishhelper.config.disable"));
            client.data.toggleTotemtime = false;
        }
        else{
            toggleTotemButton.setMessage(Text.translatable("fishhelper.config.enable"));
            client.data.toggleTotemtime = true;
        }
        client.configManage.save();
    }

    int getRegularX() {
        return  mc.getWindow().getScaledWidth() / 2 - width / 2;
    }

    int getRegularY() {
        return mc.getWindow().getScaledHeight() / 2 - height / 2;
    }

}
