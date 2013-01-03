.. _processing.processes.unique:

.. warning:: Document Status: **Requires copyedit review and addition info (MP)**

Unique
======

Description
-----------

The ``gs:Unique`` process takes a feature collection and returns the unique values for a given attribute in that feature collection. The new feature collection will contain as many features as there are unique values for the selected attribute, with eahch feature containing a single attribute with the same name as the attribute selected for extracting the unique values.

.. figure:: img/unique.png

   *gs:Unique*

Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.fcin` and returns :ref:`processing.processes.formats.fcout`.

Inputs
~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
     - Required
   * - ``features``
     - The input feature collection
     - :ref:`SimpleFeatureCollection <processing.processes.formats.fcin>`
     - Yes     
   * - ``attribute``
     - Attribute to consider for extracting unique values
     - String
     - Yes

Outputs
~~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``result``
     - Resulting feature collection of unique values
     - :ref:`SimpleFeatureCollection <processing.processes.formats.fcout>`


Usage notes
-----------

* The name of the ``attribute`` is case-sensitive.
* If the ``attribute`` selected is of type ``String``, comparison between values is case-sensitive.


Examples
--------

The following example shows how to generate a feature collection consisting of only those countries where volcanoes can be found. To accomplish this, the ``world:volcanoes`` and ``world:borders`` layers are intersected using :ref:`gs:IntersectionFeatureCollection <processing.processes.vector.intersectionfc>` to get a feature collection with volcano information. After that, the ``gs:Unique`` process is run on that feature collection to get the list of different country names.

Input parameters for ``gs:IntersectionFeatureCollection``:

* ``first feature collection``: ``world:borders``
* ``second feature collection``: ``world:volcanoes``
* ``first attributes to retain``: ``NAME``
* ``second attributes to retain``: 
* ``intersectionMode``: ``SECOND``
* ``enableArea``: false
* ``enablePercent``: false

:download:`Download complete XML request <xml/uniqueexample.xml>`.

.. figure:: img/uniqueexampleUI.png

   *gs:Intersection example parameters*

Input parameters for ``gs:Unique``:

* ``features``: Output from ``gs:IntersectionFeatureCollection``
* ``attribute``: ``borders_NAME``

.. figure:: img/uniqueexampleUI2.png

   *gs:Unique example parameters*

.. todo:: And the result?





