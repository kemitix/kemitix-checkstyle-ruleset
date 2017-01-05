package net.kemitix.checkstyle.ruleset.builder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * A single Checkstyle Check.
 *
 * @author Paul Campbell (paul.campbell@hubio.com)
 */
@ToString
@Setter
@Getter
public class Rule {

    /**
     * The name of the rule's Check class.
     */
    private String name;

    /**
     * The parent module.
     */
    private RuleParent parent;

    /**
     * The first level the rule is enabled on.
     */
    private RuleLevel level;

    /**
     * Whether the rule is enabled.
     */
    private boolean enabled;

    /**
     * The source of the rule.
     */
    private RuleSource source;

    /**
     * URI for full official documentation.
     */
    private URI uri;

    /**
     * Flag to indicate rules that can not be suppressed (via {@code @SuppressWarnings}.
     */
    private boolean insuppressible;

    /**
     * Configuration properties.
     */
    private Map<String, String> properties = new HashMap<>();
}
