package net.pmkjun.pyrofishinghelper.gui;

import java.util.Arrays;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
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
        String[] fishCounterData = new String[10];
        for(int i = 0; i < 6; i++) {
            if(client.data.toggleCounterMode == FishCounterMode.PERCENTAGE){
                fishCounterData[i] = String.format("%.2f",
                    (double)client.data.fish_Count[i]/Arrays.stream(client.data.fish_Count).sum()*100)+"%";
            }
            else if(client.data.toggleCounterMode == FishCounterMode.COUNT){
                fishCounterData[i] = String.format("%d", client.data.fish_Count[i])+"마리";
            }
            else{
                fishCounterData[i] = String.format("%.2f",
                    (double)client.data.fish_Count[i]/Arrays.stream(client.data.fish_Count).sum()*100)+
                    "(" + String.format("%d", client.data.fish_Count[i])+")"+"%";
            }
        }
        fishCounterData[6] = Integer.toString(Arrays.stream(client.data.fish_Count).sum()) + "마리";
            
        fishCounterData[7] = " ";
        fishCounterData[8] = String.format("%.1f", Earning.getMoney())+"원";
        fishCounterData[9] = String.format("%.1f", Earning.getEntropy())+"E";
        

        if(client.data.toggleFishCounter || client.data.toggleEarningCalculator)
            render(context, fishCounterData);
        
            
    }

    private void render(DrawContext context, String[] fishCounterData){
        int line = 0, x, y, elements_counts = 0;
        int start_point = 0, end_point = 10;
        String[] left_fields = {"커먼","언커먼","레어","에픽","레전더리","신화", "합", " ", "골드 환산", "엔트로피 환산"};

        if(client.data.toggleFishCounter)
        {
            elements_counts += 6;
            end_point = 7;
        }
        if(client.data.toggleEarningCalculator)
        {
            elements_counts += 2;
            start_point = 8;
        }
        if(client.data.toggleFishCounter && client.data.toggleEarningCalculator) 
        {
            elements_counts += 1;
            start_point = 0;
            end_point = 10;
        }

        x = 2 + (mc.getWindow().getScaledWidth() - (116)) * client.data.Counter_xpos / 1000;
        y = 2 + (mc.getWindow().getScaledHeight() - (12) * (elements_counts+1) - 4) * client.data.Counter_ypos / 1000;
            
        for(int i = start_point; i < end_point ; i++){
            renderWithoutTexture(line++, context, x, y, left_fields[i], fishCounterData[i]);
        }
    }

    private void renderWithoutTexture(int i, DrawContext context,int x, int y, String left_field, String right_field){
        MatrixStack poseStack = context.getMatrices();
        this.font = this.mc.textRenderer;
        int width = 50, text_width;

        text_width = this.font.getWidth(left_field);

        poseStack.push();
        poseStack.translate((x + 2), y+4 + (12) * i, 0.0D);
        poseStack.scale(1f/1.1f, 1f/1.1f, 1f/1.1f);
        context.drawTextWithShadow(this.font, Text.literal(left_field), width - text_width, 0, 0xFFFFFF);
        poseStack.scale(1.1f, 1.1f, 1.1f);
		
        poseStack.pop();

        text_width = this.font.getWidth(right_field);
        poseStack.push();
        poseStack.translate((x + 2 + 50 + 16), y+4 + (12) * i, 0.0D);
        poseStack.scale(1f/1.1f, 1f/1.1f, 1f/1.1f);
        context.drawTextWithShadow(this.font, Text.literal(right_field), width - text_width, 0, 0xFFFFFF);
        
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
