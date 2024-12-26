import React from 'react'
import './Input.module.css'

interface Props {
    type: string
    name: string
    placeholder: string
    value: string | number | undefined
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
    minLength?: number
    maxLength?: number
    required: boolean
    disabled?: boolean
}

const Input = ({ type, name, placeholder, value, onChange, minLength, maxLength, required, disabled }: Props) => {
    return (
        <input
            type={type}
            name={name}
            placeholder={placeholder}
            value={value}
            onChange={onChange}
            minLength={minLength}
            maxLength={maxLength}
            required={required}
            disabled={disabled}
        />
    )
}

export default Input
