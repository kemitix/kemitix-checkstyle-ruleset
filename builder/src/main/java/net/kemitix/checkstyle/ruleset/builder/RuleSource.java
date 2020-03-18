package net.kemitix.checkstyle.ruleset.builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The origin of the rule.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Setter
@Getter
@NoArgsConstructor
public class RuleSource {

    private String name;
    private boolean enabled;
    private String basePackage;

}
