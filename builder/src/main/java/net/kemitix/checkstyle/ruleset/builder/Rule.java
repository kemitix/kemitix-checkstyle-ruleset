package net.kemitix.checkstyle.ruleset.builder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author Paul Campbell (paul.campbell@hubio.com)
 */
@ToString
@Setter
@Getter
class Rule {

    private String name;

    private RuleParent parent;

    private RuleLevel level;

    private boolean enabled;

    private RuleSource source;

    private URI uri;

    private Map<String, String> properties = new HashMap<>();

    private List<String> body = new ArrayList<>();
}
