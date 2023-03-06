import PropTypes from 'prop-types'
import get from 'lodash.get'
import set from 'lodash.set'
import React, { useReducer,useState } from 'react'
import { ToastContainer,toast } from 'react-toastify'

import {Modal,Button} from 'react-bootstrap'

import { getShareUrl, parseParams } from '../utils/ApiUtils'

export const defaultInitializrContext = {
  values: {
    project: '',
    language: '',
    boot: '',
    meta: {
      name: '',
      group: '',
      artifact: '',
      description: '',
      packaging: '',
      packageName: '',
      java: '',
    },
    dependencies: [],
  },
  share: '',
  errors: {},
  warnings: {},
  zone:'',
  tenant:'',
  cluster:''
}

export function reducer(state, action) {
  switch (action.type) {
    case 'COMPLETE': {
      const json = get(action, 'payload')
      const defaultValues = {
        ...get(json, 'defaultValues'),
        meta: get(json, 'defaultValues.meta'),
      }
      return {
        values: defaultValues,
        share: getShareUrl(defaultValues),
        errors: {},
        warnings: {},
      }
    }
    case 'UPDATE': {
      const changes = get(action, 'payload')
      let errors = { ...state.errors }
      let meta = { ...get(state, 'values.meta') }
      if (get(changes, 'meta')) {
        meta = { ...meta, ...get(changes, 'meta') }
      }
      if (get(changes, 'boot')) {
        const { boot, ...err } = errors
        errors = err
      }
      if (get(changes, 'meta.group') !== undefined) {
        set(
          meta,
          'packageName',
          `${get(meta, 'group')}.${get(meta, 'artifact')}`
        )
      }
      if (get(changes, 'meta.artifact') !== undefined) {
        set(
          meta,
          'packageName',
          `${get(meta, 'group')}.${get(meta, 'artifact')}`
        )
        set(meta, 'name', `${get(meta, 'artifact')}`)
      }
      const values = {
        ...get(state, 'values'),
        ...changes,
        meta,
      }
      return { ...state, values, share: getShareUrl(values), errors }
    }
    case 'LOAD': {
      const params = get(action, 'payload.params')
      const lists = get(action, 'payload.lists')
      const { values, errors, warnings } = parseParams(
        state.values,
        params,
        lists
      )
      return { ...state, values, errors, warnings, share: getShareUrl(values) }
    }
    case 'ADD_DEPENDENCY': {
      const dependency = get(action, 'payload.id')
      const values = { ...get(state, 'values') }
      values.dependencies = [...get(values, 'dependencies'), dependency]
      return { ...state, values, share: getShareUrl(values) }
    }
    case 'REMOVE_DEPENDENCY': {
      const dependency = get(action, 'payload.id')
      const values = { ...get(state, 'values') }
      values.dependencies = [
        ...get(values, 'dependencies').filter(dep => dep !== dependency),
      ]
      return { ...state, values, share: getShareUrl(values) }
    }
    case 'CLEAR_WARNINGS': {
      return { ...state, warnings: {} }
    }
    default:
      return state
  }
}

export const InitializrContext = React.createContext({
  ...defaultInitializrContext,
})

export function InitializrProvider({ children }) {
const [zone,setzone]=useState("");
const [tenant,setenant]=useState("1000057");
const [cluster,setcluster]=useState("hera");


const [chmod,setchmod]=useState(false);
const changezone=(event)=>{
   setzone(event.target.value);
   console.log(event.target.value);
   setchmod(true);
 // toast.success(`Zone: ${zone} | tenant: ${tenant} | cluster: ${cluster}`)
  toast.success(`Zone: ${zone} | tenant: ${tenant} | cluster: ${cluster}`, {
                                                                                                 position: toast.POSITION.TOP_RIGHT
                                                                                              }
 );
};
const changetenant=(event)=>{
   setenant("1000057");
    setchmod(true);
    toast.success(`Zone: ${zone} | tenant: ${tenant} | cluster: ${cluster}`, {
                                                                                                position: toast.POSITION.TOP_RIGHT
                                                                                             }
);
};
const changecluster=(event)=>{
   setcluster("hera");
    setchmod(true);
 toast.success(`Zone: ${zone} | tenant: ${tenant} | cluster: ${cluster}`, {
                                                                                                position: toast.POSITION.TOP_RIGHT
                                                                                             }
);};
const unload=()=>{
    setchmod(false);
};

  const [state, dispatch] = useReducer(reducer, { ...defaultInitializrContext })
  console.log(`Modal:${chmod}`);
  return (
    <InitializrContext.Provider value={{ ...state, dispatch }}>
    <ToastContainer/>

      <nav class="nav nav-pills nav-stacked">
        <select onChange={changezone}>
          <option value="aws-hdfc-beta-mumbai">AWS Beta Mumbai</option>
          <option value="aws-hdfc-pp-mumbai">AWS PP Mumbai</option>
          <option value="aws-hdfc-prod-mumbai">AWS Prod Mumbai</option>
          <option value="aws-hdfc-staging-mumbai">AWS Staging Mumbai</option>
        </select>
         <select onChange={changetenant}>
              <option value="1000057i">1000057</option>
            </select>
             <select onchange={changecluster}>
                      <option value="hera">Hera</option>
                    </select>
            </nav>

      {children}
    </InitializrContext.Provider>
  )
}

InitializrProvider.defaultProps = {
  children: null,
}

InitializrProvider.propTypes = {
  children: PropTypes.node,
}
