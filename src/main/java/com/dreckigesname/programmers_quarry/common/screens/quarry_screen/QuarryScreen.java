package com.dreckigesname.programmers_quarry.common.screens.quarry_screen;

import com.dreckigesname.programmers_quarry.ProgrammersQuarry;
import com.dreckigesname.programmers_quarry.common.block_entities.QuarryBlockEntity;
import com.dreckigesname.programmers_quarry.common.menus.QuarryMenu;
import com.dreckigesname.programmers_quarry.common.screens.util.EditBox2D;
import com.dreckigesname.programmers_quarry.packets.PacketHandler;
import com.dreckigesname.programmers_quarry.packets.messages.QuarryCodeChanged;
import com.dreckigesname.programmers_quarry.packets.messages.QuarrySetRunning;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class QuarryScreen extends AbstractContainerScreen<QuarryMenu> {

    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(ProgrammersQuarry.MOD_ID, "textures/screens/quarry/background.png");

    private EditBox2D textField;
    private Button startButton;
    private final QuarryBlockEntity blockEntity;

    public QuarryScreen(QuarryMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.blockEntity = menu.getBlockEntity();
        this.imageWidth = 256;
        this.imageHeight = 203;
    }


    private void sendChangedPacket(){
        PacketHandler.CHANNEL.sendToServer(new QuarryCodeChanged(textField.rowText));
    }

    private void sendRunningPacket(boolean running){
        PacketHandler.CHANNEL.sendToServer(new QuarrySetRunning(running));
    }

    @Override
    public void onClose() {
        sendChangedPacket();
        super.onClose();
    }

    @Override
    protected void init() {
        super.init();
        this.textField = new EditBox2D(this.font, leftPos + 28, topPos + 10, 150, 100, new TextComponent(""), blockEntity.code);
        this.startButton = new Button(leftPos + 200, topPos + 50, 50, 20, new TextComponent("Start"), button -> {
            sendChangedPacket();
            sendRunningPacket(true);
        });
        this.addWidget(this.textField);
        this.addWidget(startButton);
        this.setInitialFocus(this.textField);
    }

    @Override
    public void resize(Minecraft p_96575_, int p_96576_, int p_96577_) {
        super.resize(p_96575_, p_96576_, p_96577_);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderFg(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }


    public void renderFg(PoseStack poseStack, int mouseX, int mouseY, float partialTicks){
        this.textField.render(poseStack, mouseX, mouseY, partialTicks);
        this.startButton.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_LOCATION);
        this.blit(poseStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
        this.font.draw(p_97808_, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
    }
}
