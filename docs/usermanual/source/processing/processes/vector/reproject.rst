.. _processing.processes.vector.reproject:

.. warning:: Document Status: Requires technical review

Reproject
==========

Description
-----------

The ``gs:Reproject`` process reprojects the features in a feature collection into a given :term:`CRS`.

The process takes the native feature collection :term:`CRS` as the origin one for the reprojection. However, a different origin CRS can be forced in case the native one is not correct.

.. figure:: img/reproject.png

   *gs:Reproject*

Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.fcin` and returns :ref:`processing.processes.formats.fcout`.

Inputs
^^^^^^

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
     - Required
   * - ``features``
     - Input feature collection too reproject
     - SimpleFeatureCollection
     - Yes
   * - ``forcedCRS``
     - :term:`CRS` to used instead of the original one of the input feature collection
     - CoordinateReferenceSystem
     - No
   * - ``targetCRS``
     - The :term:`CRS` to reproject input features into. The resulting feature collection will use this :term:`CRS`
     - CoordinateReferenceSystem
     - No     

Outputs
^^^^^^^

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``result``
     - The reprojected feature collection in the selected :term:`CRS`
     - SimpleFeatureCollection


Usage notes
--------------

* If no value is provided for the ``forcedCRS`` input parameter, the native :term:`CRS` is used. Otherwise, the provided :term:`CRS` is used and the native one is ignored. This can be used to correct a wrongly assigned :term:`CRS` for a give feature collection.
* The feature collection can contain geometries of any type.
* Only the default geometries are reprojected. Other additional attributes containing geometries will not be reprojected. Attributes are copied directly from the input feature collection into the output feature collection, including those with geometries. 
* Attributes containing values that depend on the :term:`CRS` (such as those depending on its units, like areas or lenghts), are not modified neither, so they should be updated independently to make sense with the new reprojected geometries.

Examples
---------

The following example shows the result of reprojection the ``usa:states`` feature collection from it original CRS (EPSG:4326) into the Albers Equal Area (EPSG:45556) one.

Input parameters:

* ``features``: *usa:states*
* ``forcedCRS``:
* ``targetCRS``: EPSG:45556

.. figure:: img/reprojectexampleUI.png

   *gs:Reproject example parameters*

:download:`Download complete XMl request <xml/reprojectexample.xml>`.

.. image:: img/reprojectexample.png

   *gs:Reproject example output*

Related processes
--------------------

- This process is of particular interest for those ones taking two or more feature collections as inputs, in which all of them have to use the same CRS. Most overlay processes such as ``gs:IntersectionFeatureCollection`` or ``gs:InclusionFeatureCollection`` are of this kind. By using the ``gs:Reproject`` process you can reproject all input feature collections into a common :term:`CRS`, so they can be used together as input.
- Use this process to change the CRS of a feature collection from a geographic CRS into a projected CRS, for those processes in which is recommended or needed to use such ones, such as the ``gs:Buffer`` process.
- To reproject a single geometry, use the ``gs:ReprojectGeometry`` process instead.

Additional information
-----------------------

To know more about Coordinate transformations, check the following link:

- http://kartoweb.itc.nl/geometrics/coordinate%20transformations/coordtrans.html

For more information on available CRSs, go to the following sites:

- http://www.epsg-registry.org/
- http://spatialreference.org/

