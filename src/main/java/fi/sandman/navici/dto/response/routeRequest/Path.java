package fi.sandman.navici.dto.response.routeRequest;

import java.util.List;

/**
 * For wrapping {@link Point}, {@link Line} and {@link Walk} in the same
 * org.simpleframework.xml.ElementListUnion from RouteResponse xml.
 * 
 * <code>
 * 
 * <route>
 * 	<point></point>
 *  <line></line>
 *  <walk></walk>
 *  <point></point>
 * </route>
 * 
 * </code>
 * 
 * This way we can read the actual path (bus lines and walking bits) in the
 * right order to the ElemenListUnion
 * 
 * @author Jouni Latvatalo <jouni.latvatalo@gmail.com>
 * 
 */
public abstract class Path {

	public abstract List<PathPoint> getPathPoints();

	public abstract PathType getPathType();

}
