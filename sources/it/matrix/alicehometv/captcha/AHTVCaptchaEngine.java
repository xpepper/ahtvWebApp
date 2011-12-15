package it.matrix.alicehometv.captcha;

import java.awt.Color;
import java.awt.image.ImageFilter;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.LineRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.LineTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;

public class AHTVCaptchaEngine extends ListImageCaptchaEngine {

    protected void buildInitialFactories() {
        com.jhlabs.image.WaterFilter water = new com.jhlabs.image.WaterFilter();

        water.setAmplitude(0d);
        water.setAntialias(true);
        water.setPhase(5d);
        water.setWavelength(150d);

        ImageDeformation backDef = new ImageDeformationByFilters(
                new ImageFilter[]{});
        ImageDeformation textDef = new ImageDeformationByFilters(
                new ImageFilter[]{});
        ImageDeformation postDef = new ImageDeformationByFilters(
                new ImageFilter[]{water});

        com.octo.captcha.component.word.wordgenerator.WordGenerator dictionnaryWords = new com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator(
                new com.octo.captcha.component.word.FileDictionary(
                        "toddlist"));

        TextPaster randomPaster = new DecoratedRandomTextPaster(new Integer(6), new Integer(6),
                new SingleColorGenerator(Color.black)
               , new TextDecorator[]{new BaffleTextDecorator(new Integer(0), Color.white)});

        
        BackgroundGenerator back = new UniColorBackgroundGenerator(new Integer(220), new Integer(60), Color.white);

        FontGenerator shearedFont = new RandomFontGenerator(new Integer(20),
                new Integer(30));

        com.octo.captcha.component.image.wordtoimage.WordToImage word2image;
        word2image = new DeformedComposedWordToImage(shearedFont, back, randomPaster,
                backDef,
                textDef,
                postDef
        );

        this.addFactory(
                new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords, word2image));
    }
}
