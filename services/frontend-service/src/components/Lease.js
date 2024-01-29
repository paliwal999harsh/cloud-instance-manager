// Lease.js
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import 'react-datepicker/dist/react-datepicker.css';
import Loading from './Loading';

const Lease = () => {
  // State and functions for handling lease functionality
  const [formData, setFormData] = useState({
    instanceName: '',
    startDate: '',
    endDate: '',
    startTime: '09:00',
    endTime: '18:00',
    reason: '',
    alwaysOn: false,
    weekendOn: false,
  });

  const [instanceList, setInstanceList] = useState([]);
  const [loading, setLoading] = useState(true);

  const [formErrors, setFormErrors] = useState({
    instanceName: '',
    startDate: '',
    endDate: '',
    startTime: '',
    endTime: '',
    reason: '',
  });

  const validateForm = () => {
    let isValid = true;
    const errors = {
      instanceName: '',
      startDate: '',
      endDate: '',
      startTime: '',
      endTime: '',
      reason: '',
    };

    // Perform validation based on your rules
    if (!formData.instanceName) {
      isValid = false;
      errors.instanceName = 'Instance is required';
    }

    setFormErrors(errors);
    return isValid;
  };


  useEffect(() => {
    const fetchInstanceList = async () => {
      try {
        const response = await axios.get('/cim/api/v1/instance/listInstances?cloud=AWS');
        setInstanceList(response.data);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching instance list:', error);
        setLoading(false);
      }
    };

    fetchInstanceList();
  }, []);

  useEffect(() => {
    console.log('Form data after change', formData);
  }, [formData]);

  if (loading) {
    return <Loading />;
  }

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Validate the form before submission
    if (validateForm()) {
      const response = axios.put("/cim/api/v1/lease", formData);
      console.log(response);
      console.log('Form submitted:', formData);
    } else {
      console.log('Form validation failed');
    }
  };

  const handleCancel = () => {
    // TODO: Add logic to handle form cancellation (e.g., reset form fields)
    console.log("handleCancel called");
    setFormData({
      instanceName: '',
      startDate: '',
      endDate: '',
      startTime: '',
      endTime: '',
      reason: '',
      alwaysOn: false,
      weekendOn: false,
    });
  };

  return (
    <div id="lease-create-form" className="mt-6 flex items-baseline justify-center">
      <form onSubmit={handleSubmit} method="post">
        <div className="space-y-2">
          <div className="border-b border-gray-900/10 pb-4">
            <h2 className="text-base font-semibold leading-7 text-gray-900">Create New Lease</h2>
            <p className="text-sm leading-6 text-gray-600">Please fill the form.</p>
            <div className="sm:col-span-3">
              <label htmlFor="instances" className="mt-2 block text-sm font-medium leading-6 text-gray-900">Select your instance</label>
              <div className="mt-1">
                <select id="instances" name="instanceName" value={formData.instanceName} onChange={handleChange}
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6 p-2.5">
                  <option key="default" value="">Select your instance</option>
                  {instanceList.map(instance => (
                    <option key={instance.instanceName} value={instance.instanceName}>
                      {instance.instanceName}
                    </option>
                  ))}
                </select>
                {formErrors.instanceName && (<p className="text-red-500 text-sm mt-1">{formErrors.instanceName}</p>)}
              </div>
            </div>
            <div className="sm:col-span-3">
              <label htmlFor="date" className="mt-2 block text-sm font-medium leading-6 text-gray-900">Select Date Range</label>
              <div className="flex items-center mt-1">
                <div className='relative'>
                  <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                    <svg className="w-4 h-4 text-gray-500 text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                      <path d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z" />
                    </svg>
                  </div>
                  <div className="relative">
                    <input name="startDate" type="text" value={formData.startDate} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Enter Start Date (YYYY-MM-DD)" />
                  </div>
                </div>
                <span className="mx-4 text-gray-500">to</span>
                <div className='relative'>
                  <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                    <svg className="w-4 h-4 text-gray-500 text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                      <path d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z" />
                    </svg>
                  </div>
                  <div className="relative">
                    <input name="endDate" type="text" value={formData.endDate} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Enter End Date (YYYY-MM-DD)" />
                  </div>
                </div>
              </div>
            </div>
            <div className="sm:col-span-3">
              <label htmlFor="time" className="mt-2 block text-sm font-medium leading-6 text-gray-900">Select Time Period</label>
              <div className='flex items-center mt-1'>
                <div className="relative">
                  <input name="startTime" type="text" value={formData.startTime} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Enter Start Time (HH:MM)" />
                </div>
                <span className="mx-4 text-gray-500">to</span>
                <div className="relative">
                  <input name="endTime" type="text" value={formData.endTime} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Enter End Time (HH:MM)" />
                </div>
              </div>
            </div>
            <div className="sm:col-span-3">
              <label htmlFor="reason" className="mt-2 block text-sm font-medium leading-6 text-gray-900">Provide Reason</label>
              <div className="mt-2">
                <input type="text" name="reason" id="reason" value={formData.reason} onChange={handleChange} className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 p-2.5" />
              </div>
            </div>
          </div>

          <div className="border-b border-gray-900/10">
            <h2 className="text-base font-semibold leading-7 text-gray-900">Show Advance options</h2>
            <p className="mt-1 text-sm leading-6 text-gray-600">Click here to see more options.</p>
            <div className="mt-10 space-y-10">
              <fieldset>
                <legend className="text-sm font-semibold leading-6 text-gray-900">Advance Options</legend>
                <div className="mt-6 space-y-6">
                  <div className="relative flex gap-x-3">
                    <div className="flex h-6 items-center">
                      <input id="AlwaysOn" name="alwaysOn" type="checkbox" value={formData.alwaysOn} onChange={handleChange} className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-600" />
                    </div>
                    <div className="text-sm leading-6">
                      <label htmlFor="AlwaysOn" className="font-medium text-gray-900">Always On</label>
                      <p className="text-gray-500">Keep running the instance 24 hours of the day.</p>
                    </div>
                  </div>
                  <div className="relative flex gap-x-3">
                    <div className="flex h-6 items-center">
                      <input id="WeekendOn" name="weekendOn" type="checkbox" value={formData.weekendOn} onChange={handleChange} className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-600" />
                    </div>
                    <div className="text-sm leading-6">
                      <label htmlFor="WeekendOn" className="font-medium text-gray-900">Weekend On</label>
                      <p className="text-gray-500">Keep the instance running on weekends as well.</p>
                    </div>
                  </div>
                </div>
              </fieldset>
            </div>
          </div>
          <div className="mt-6 flex items-center justify-end gap-x-6">
            <button type="button" onClick={handleCancel} className="text-sm font-semibold leading-6 text-gray-900">Cancel</button>
            <button type="submit" className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Submit</button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default Lease;
