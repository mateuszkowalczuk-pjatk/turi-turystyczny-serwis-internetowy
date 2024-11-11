import React from 'react'
import Input from '../../Controls/Input'
import styles from './AuthInput.module.css'

interface Props {
    type: string
    name: string
    placeholder: string
    value: string | number
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
    minLength?: number
    maxLength?: number
    required: boolean
    disabled?: boolean
}

const AuthInput = ({ type, name, placeholder, value, onChange, minLength, maxLength, required, disabled }: Props) => {
    return (
        <div className={styles.input}>
            <Input
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
        </div>
    )
}

export default AuthInput
