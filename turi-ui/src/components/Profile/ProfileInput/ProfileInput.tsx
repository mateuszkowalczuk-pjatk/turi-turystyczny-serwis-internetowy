import styles from './ProfileInput.module.css'
import Input from '../../Controls/Input'
import React from 'react'

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

export const ProfileInput = ({
    type,
    name,
    placeholder,
    value,
    onChange,
    minLength,
    maxLength,
    required,
    disabled
}: Props) => {
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

export default ProfileInput
