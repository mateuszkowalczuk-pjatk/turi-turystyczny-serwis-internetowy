import React from 'react'
import { useTranslation } from 'react-i18next'
import { PriceType } from '../../../types/attraction.ts'
import styles from './TourismPriceTypeSelect.module.css'

interface Props {
    value: PriceType
    onChange: (value: PriceType) => void
    disabled: boolean
}

const TourismPriceTypeSelect = ({ value, onChange, disabled }: Props) => {
    const { t } = useTranslation()

    return (
        <div className={styles.panel}>
            <select
                className={styles.select}
                value={value}
                onChange={(e: React.ChangeEvent<HTMLSelectElement>) => onChange(e.target.value as unknown as PriceType)}
                disabled={disabled}
            >
                <option value={PriceType.HOUR}>{t('tourism.touristic-place-price-type-hour')}</option>
                <option value={PriceType.PERSON}>{t('tourism.touristic-place-price-type-person')}</option>
                <option value={PriceType.ITEM}>{t('tourism.touristic-place-price-type-item')}</option>
            </select>
        </div>
    )
}

export default TourismPriceTypeSelect
