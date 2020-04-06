/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package sirius.kernel.di.transformers;

import sirius.kernel.Sirius;
import sirius.kernel.di.std.Priorized;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as automatically transformed.
 * <p>
 * If a {@link Transformer} simply invokes the constructor of the target class and passes in the source instance,
 * this annotation can be used to synthesize such a transformer.
 * <p>
 * To achieve this, the target class must wear this annotation and specify the source class as well as optionally
 * a priority (if another transformer is to be overwritten / bypassed).
 * <p>
 * Note that the annotated class has to provide a public single argument constructor which takes the source instance
 * as parameter. This constructor can throw a {@link IllegalArgumentException} to signal that the transformer should
 * simply return <tt>null</tt> so that other transformers (if available) can try their luck.
 * <p>
 * If the transformer should depend on a {@link Sirius#isFrameworkEnabled(String) framework} to be enabled,
 * the {@link sirius.kernel.di.std.Framework} annotation can be used to express this.
 * <p>
 * Note that no {@link sirius.kernel.di.std.Register} needs (and also should) be present next to this annotation.
 * However, all instances generated by the transformer are {@link sirius.kernel.di.GlobalContext#wire(Object) wired}
 * automatically and can therefore use annotations like {@link sirius.kernel.di.std.Part} etc.
 *
 * @see Transformer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AutoTransform {

    /**
     * Specifies the source class to transform from into the annotated class.
     *
     * @return the source class of the synthesized transformer
     */
    Class<?> source();

    /**
     * Specifies the target class / interface to transform to.
     *
     * @return the target class of the synthesized transformer
     */
    Class<?> target();

    /**
     * Permits to specify a priority.
     *
     * @return the priority to use for the synthesized transfomer
     */
    int priority() default Priorized.DEFAULT_PRIORITY;
}