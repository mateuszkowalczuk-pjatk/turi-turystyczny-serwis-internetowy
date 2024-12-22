import React from 'react'
import Input from '../../Shared/Controls/Input'
import styles from './PremiumPersonalInput.module.css'

interface Props {
    firstType: string
    firstName: string
    firstPlaceholder: string
    firstValue: string | number | undefined
    firstOnChange: (e: React.ChangeEvent<HTMLInputElement>) => void
    firstMinLength?: number
    firstMaxLength?: number
    firstRequired: boolean
    firstDisabled?: boolean
    secondType: string
    secondName: string
    secondPlaceholder: string
    secondValue: string | number | undefined
    secondOnChange: (e: React.ChangeEvent<HTMLInputElement>) => void
    secondMinLength?: number
    secondMaxLength?: number
    secondRequired: boolean
    secondDisabled?: boolean
}

const PremiumPersonalInput = ({
    firstType,
    firstName,
    firstPlaceholder,
    firstValue,
    firstOnChange,
    firstMinLength,
    firstMaxLength,
    firstRequired,
    firstDisabled,
    secondType,
    secondName,
    secondPlaceholder,
    secondValue,
    secondOnChange,
    secondMinLength,
    secondMaxLength,
    secondRequired,
    secondDisabled
}: Props) => {
    return (
        <div className={styles.personal}>
            <Input
                type={firstType}
                name={firstName}
                placeholder={firstPlaceholder}
                value={firstValue}
                onChange={firstOnChange}
                minLength={firstMinLength}
                maxLength={firstMaxLength}
                required={firstRequired}
                disabled={firstDisabled}
            />
            <Input
                type={secondType}
                name={secondName}
                placeholder={secondPlaceholder}
                value={secondValue}
                onChange={secondOnChange}
                minLength={secondMinLength}
                maxLength={secondMaxLength}
                required={secondRequired}
                disabled={secondDisabled}
            />
        </div>
    )
}

export default PremiumPersonalInput
