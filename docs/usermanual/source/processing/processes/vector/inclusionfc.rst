.. _processing.processes.vector.inclusionfc:

.. warning:: Document Status: **Requires copyedit review (MP)**

InclusionFeatureCollection
==========================

Description
-----------

The ``gs:InclusionFeatureCollection`` process takes two feature collections and returns one that contains the features from the first input collection that are spatially contained within at least one feature from the second one.

This process acts as a feature filter and does not modify either geometries or other attributes.

.. figure:: img/inclusionfc.png

   *gs:InclusionFeatureCollection*

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
   * - ``first feature collection``
     - First feature collection, which contains the features that will be filtered
     - :ref:`SimpleFeatureCollection <processing.processes.formats.fcin>`
     - Yes
   * - ``second feature collection``
     - Second feature collection. Only features from the first feature collection contained within the features of this feature collection will be included in the output.
     - :ref:`SimpleFeatureCollection <processing.processes.formats.fcin>`
     - Yes

Outputs
~~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``result``
     - Output feature collection
     - :ref:`SimpleFeatureCollection <processing.processes.formats.fcout>`

Usage notes
-----------

* Input feature collections can be of any geometry type, and the process performs no geometry check of any kind. However, using a feature collection of polygons for the ``second feature collection`` parameter is the most common case.

Examples
--------

This example uses the ``medford:streets`` and ``medford:zoning`` layers to return only those streets which are completely contained within a zone.

Input parameters
    
* ``first feature collection``: ``medford:streets``
* ``second feature collection``: ``medford:zoning``      

.. figure:: img/inclusionfcexampleUI.png

   *gs:InclusionFeatureCollection example parameters*

:download:`download complete XML request <xml/inclusionfcexample.xml>`.

The image below represents a close up of both input feature collections along with the output one (in black). Notice how streets that are partially contained within a zone are not part of the final feature collection, as this process requires the features to be *fully contained*.

.. figure:: img/inclusionfcexample.png

   *gs:InclusionFeatureCollection example output*

Related processes
-----------------

* The ``gs:InclusionFeatureCollectionProcess`` differs from other overlay processes like :ref:`gs:Clip <processing.processes.vector.clip>` or :ref:`gs:IntersectionFeatureCollection <processing.processes.vector.intersectionfc>` in that the geometries are not modified, and the secondary feature collection is used to filter—not alter—the geometries in the first feature collection.