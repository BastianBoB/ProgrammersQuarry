package com.dreckigesname.programmers_quarry.common.screens.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EditBox2D extends AbstractWidget implements Widget, GuiEventListener {

    private final Font font;
    public int backGroundColor = 0xFF000000;
    public int outlineColor = 0xFF030303;
    public int cursorColor = 0xFFFF8000;
    public int textColor = 0xFFFFFFFF;
    private int renderTickCount;

    private static final float textScale = 0.75f;

    public List<String> rowText = new ArrayList<>();
    public static int cursorPos, cursorRow;
    public int oldCursorPos, oldCursorRow;

    public EditBox2D(Font font, int x, int y, int w, int h, Component title, List<String> rowText) {
        super(x, y, w, h, title);
        this.font = font;
        this.rowText = rowText;
        if(rowText.isEmpty()) {
            this.rowText.add("");
        }
        correctCursor();
    }


    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);
        renderTickCount++;

        //DRAW BACKGROUND
        GuiComponent.fill(poseStack, x - 2, y - 2, x + width + 2, y + height + 2, outlineColor);
        GuiComponent.fill(poseStack, x, y, x + width, y + height, backGroundColor);

        poseStack.pushPose();
        float translateFactor = 1 - textScale;
        poseStack.translate(x * translateFactor, y * translateFactor, 0);
        poseStack.scale(textScale, textScale, 1f);

        //DRAW TEXT
        for (int i = 0; i < rowText.size(); i++) {
            int strY = (int) (y + i * (stringHeight() * 1.2));
            String drawString = rowText.get(i);
            drawString(poseStack, font, drawString, x, strY, textColor);
        }

        //DRAW CURSOR
        if (renderTickCount % 40 < 20 || cursorPos != oldCursorPos || cursorRow != oldCursorRow) {
            String str = rowText.get(cursorRow);

            int cursorY = (int) (y + cursorRow * (stringHeight() * 1.2));
            int cursorX = x + stringWidth(str.substring(0, cursorPos));

            drawCursor(poseStack, cursorX, cursorY, cursorPos == rowText.get(cursorRow).length());
        }
        poseStack.popPose();

        oldCursorPos = cursorPos;
        oldCursorRow = cursorRow;
    }

    private void drawCursor(PoseStack poseStack, int x, int y, boolean vertical){
        if (vertical) {
            GuiComponent.fill(poseStack, x, y - 1 + stringHeight(), x + 5, y + stringHeight(), cursorColor);
        } else {
            GuiComponent.fill(poseStack, x - 1, y, x, y + stringHeight() - 1, cursorColor);
        }
    }

    @Override
    public void renderButton(PoseStack p_93676_, int p_93677_, int p_93678_, float p_93679_) {
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {

    }

    private int stringWidth(String str) {
        return font.width(str);
    }

    private int scaledStringWidth(String str) {
        return (int) (font.width(str) * textScale);
    }

    private int stringHeight() {
        return font.lineHeight;
    }

    @Override
    public boolean mouseClicked(double p_94125_, double p_94126_, int p_94127_) {
        if (!this.visible) {
            return false;
        } else {
            boolean flag = p_94125_ >= (double) this.x && p_94125_ < (double) (this.x + this.width) && p_94126_ >= (double) this.y && p_94126_ < (double) (this.y + this.height);
            this.setFocused(flag);
            return flag;
        }
    }

    @Override
    public boolean keyPressed(int key, int p_94746_, int p_94747_) {
        System.out.println("KEY PRESSED");

        if(key == 69) return true;

        if (key == 259) {
            if (cursorPos == 0) {
                if (cursorRow == 0) return false;

                return removeRow();
            }
            return removeChar();
        }

        if (key == 257) {
            return addRow();
        }

        if (key == 262) moveCursorPos(1);
        if (key == 263) moveCursorPos(-1);
        if (key == 264) moveCursorRow(1);
        if (key == 265) moveCursorRow(-1);
        return true;
    }

    @Override
    public boolean keyReleased(int p_94750_, int p_94751_, int p_94752_) {
        return super.keyReleased(p_94750_, p_94751_, p_94752_);
    }

    @Override
    public boolean charTyped(char c, int p_94733_) {
        return addChar(c);
    }

    private void setCursor(int row, int pos) {
        cursorRow = row;
        cursorPos = pos;
        correctCursor();
    }

    private void correctCursor() {
        cursorRow = Math.max(0, Math.min(rowText.size() - 1, cursorRow));
        cursorPos = Math.max(0, Math.min(rowText.get(cursorRow).length(), cursorPos));
    }

    private void setCursorToEndOfRow(int row) {
        setCursor(row, rowText.get(row).length());
    }

    private void moveCursorPos(int amount) {
        if (cursorPos == 0 && cursorRow > 0 && amount < 0) {
            setCursorToEndOfRow(cursorRow - 1);
        } else if (cursorPos == rowText.get(cursorRow).length() && cursorRow < rowText.size() - 1 && amount > 0) {
            setCursor(cursorRow + 1, 0);
        } else {
            setCursor(cursorRow, cursorPos + amount);
        }

    }

    private void moveCursorRow(int amount) {
        setCursor(cursorRow + amount, cursorPos);
    }

    private boolean removeRow() {
        setCursorToEndOfRow(cursorRow - 1);
        rowText.set(cursorRow, rowText.get(cursorRow) + rowText.get(cursorRow + 1));
        rowText.remove(cursorRow + 1);
        return false;
    }

    private boolean addRow() {
        rowText.add(cursorRow + 1, rowText.get(cursorRow).substring(cursorPos));
        rowText.set(cursorRow, rowText.get(cursorRow).substring(0, cursorPos));
        setCursor(cursorRow + 1, 0);
        return false;
    }

    private boolean removeChar() {
        String str = rowText.get(cursorRow);
        rowText.set(cursorRow, str.substring(0, cursorPos - 1) + str.substring(cursorPos));
        moveCursorPos(-1);
        return false;
    }

    private boolean addChar(char c) {
        String oldString = rowText.get(cursorRow);
        String newString;
        if (oldString.isEmpty()) {
            newString = Character.toString(c);
        } else {
            newString = oldString.substring(0, cursorPos) + c + oldString.substring(cursorPos);
        }
        if(scaledStringWidth(newString) > width) return false;

        rowText.set(cursorRow, newString);
        cursorPos++;
        return true;
    }

}

