package net.pmkjun.pyrofishinghelper.gui;

import java.util.Arrays;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import net.pmkjun.pyrofishinghelper.util.Earning;
import net.pmkjun.pyrofishinghelper.util.FishCounterMode;

public class FishCounterGui {
    private MinecraftClient mc;
    private FishHelperClient client;
    private TextRenderer font;

    public FishCounterGui(){
        this.mc = MinecraftClient.getInstance();
        this.client = FishHelperClient.getInstance();
    }

    public void renderTick(DrawContext context){
        String[] fishCounterData = new String[9];
        for(int i = 0; i < 6; i++) {
            if(client.data.toggleFishCounterMode == FishCounterMode.PERCENTAGE){
                fishCounterData[i] = String.format("%.2f",
                    (double)client.data.fish_Count[i]/Arrays.stream(client.data.fish_Count).sum()*100)+"%";
            }
            else{
                fishCounterData[i] = String.format("%.2f", client.data.fish_Count[i])+"마리";
            }
            
            fishCounterData[6] = Integer.toString(Arrays.stream(client.data.fish_Count).sum());
            
            fishCounterData[7] = String.format("%.3f", Earning.getMoney())+"원";
            fishCounterData[8] = String.format("%.3f", Earning.getEntropy())+"E";
        }

        if(client.data.toggleFishCounter){
            render(context, fishCounterData);
        }
            
    }

    private void render(DrawContext context, String[] fishCounterData){
        if(client.data.toggleGradeProbability){
            int fish_counts = 6, line = 0, x, y, elements_counts = 0;
            String[] rarity = {"커먼","언커먼","레어","에픽","레전더리","신화"};

            if(client.data.toggleFishCounter)
                elements_counts += 6;
            if(client.data.toggleEarningCalculator)
                elements_counts += 2;

            x = 2 + (mc.getWindow().getScaledWidth() - (16 + 2 + 27+50)) * client.data.Counter_xpos / 1000;
            y = 2 + (mc.getWindow().getScaledHeight() - (16 + 2) * elements_counts - 2) * client.data.Counter_ypos / 1000;
            
            for(int i = 0; i < fish_counts ; i++){
                renderWithoutTexture(line++, context, x, y, rarity[i], fishCounterData[i]);
            } 
            for(int i = 6; i < 8 ; i++){
                renderWithoutTexture(line++, context, x, y, " ", fishCounterData[i]);
            }

        }
    }

    private void renderWithTexture(int i, DrawContext context,Identifier texture, int x, int y, String field){
        MatrixStack poseStack = context.getMatrices();
        int Counter_xpos, Counter_ypos;
        Counter_xpos = x;
        Counter_ypos = y;

        poseStack.push();
        poseStack.translate(Counter_xpos,Counter_ypos,0.0D);
        poseStack.scale(16f/256f, 16f/256f, 16f/256f);

        RenderSystem.setShaderTexture(0,texture);
        context.drawTexture(texture, 0, 0, 0, 0, 256, 256);
        poseStack.scale(256f/16f, 256f/16f, 256f/16f);
        poseStack.pop();

        /*if (this.client.data.toggleTotemtimeText) {
            this.font = this.mc.textRenderer;
            int minute = second / 60;
            second -= minute * 60;
            poseStack.push();
            poseStack.translate((Counter_xpos + 16 + 2), Counter_ypos+4, 0.0D);
            poseStack.scale(1f/1.1f, 1f/1.1f, 1f/1.1f);
            context.drawTextWithShadow(this.font, (Text)Text.literal(String.format("%02d:%02d", new Object[] { Integer.valueOf(minute), Integer.valueOf(second) })), 0, 0, 16777215);
            poseStack.scale(1.1f, 1.1f, 1.1f);
			
            poseStack.pop();
        }*/
    }

    private void renderWithoutTexture(int i, DrawContext context,int x, int y, String left_field, String right_field){
        MatrixStack poseStack = context.getMatrices();
        this.font = this.mc.textRenderer;
        
        poseStack.push();
        poseStack.translate((x + 2), y+4 + (16 + 2) * i, 0.0D);
        poseStack.scale(1f/1.1f, 1f/1.1f, 1f/1.1f);
        context.drawTextWithShadow(this.font, Text.literal(left_field), 0, 0, 0xFFFFFF);
        poseStack.scale(1.1f, 1.1f, 1.1f);
			
        poseStack.pop();

        poseStack.push();
        poseStack.translate((x + 16 + 2 + 50), y+4 + (16 + 2) * i, 0.0D);
        poseStack.scale(1f/1.1f, 1f/1.1f, 1f/1.1f);
        context.drawTextWithShadow(this.font, Text.literal(right_field), 0, 0, 0xFFFFFF);
        
        poseStack.scale(1.1f, 1.1f, 1.1f);
			
        poseStack.pop();
        
    }

    private int getXpos(){
        return 2 + (this.mc.getWindow().getScaledWidth()-43-2) * this.client.data.Timer_xpos / 1000;
    }
    private int getYpos(){
        return 2 + (this.mc.getWindow().getScaledHeight()-18-2) * this.client.data.Timer_ypos / 1000;
    }
}
