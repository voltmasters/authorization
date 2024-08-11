#
# authorization - Authorization Service for authenticating requests from Charging Stations
# Copyright © 2024 Subhrodip Mohanta (contact@subhrodip.com)
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#

FROM amazoncorretto:21-alpine

LABEL maintainer="Subhrodip Mohanta <contact@subhrodip.com>"
LABEL group="com.subhrodip.voltmasters"
LABEL artifact="authorization"
LABEL platform="java"
LABEL name="Authorization Server"
LABEL org.opencontainers.image.source="https://github.com/voltmasters/authorization"

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

# If you are changing server port, be sure to change this as well
EXPOSE 8080

#Running the application with `prod` profile
ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=prod", "/app.jar" ]
