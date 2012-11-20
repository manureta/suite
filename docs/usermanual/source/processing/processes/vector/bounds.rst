.. _processing.processes.vector.bounds:

.. warning:: Document Status: Requires technical review

Bounds
=======

Description
-----------

The ``gs:Bounds`` process takes a feature collection and returns the bounding box of its features. The bounding box is the minimum rectangle 

.. figure:: img/bounds.png
   
   *gs:Bounds*


Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.fcin` 

Inputs
^^^^^^

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
     - Required
   * - ``features``
     - Input feature collection
     - :ref:`SimpleFeatureCollection <processing.processes.formats.fcin>`
     - Yes

Outputs
^^^^^^^

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``bounds``
     - The resulting bounding box
     - ReferencedEnvelope

Examples
---------

The following example calculates the bounding box of the ``medford:citylimits`` feature collection.

Input Parameters:

* ``features``: ``medford:citylimits``

:download:`Download complete XML request <xml/boundsexample.xml>`.

.. figure:: img/boundsexampleUI.png

   *gs:Bounds example parameters*

The output is a ``ReferencedEnvelope`` object, representing the rectangle with the following coordinates:

``-122.91114387175001 42.28876047432729 -122.77716656020742 42.39838210872112``




Usage notes
--------------

* The input feature collection can contain any type of geometries.
* The coordinates of the bounding box are expressed in the :term:`CRS` of the input feature collection.



