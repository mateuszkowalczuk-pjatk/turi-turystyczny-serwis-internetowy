import { useHooks } from '../../../hooks/shared/useHooks.ts'
import Input from '../../Shared/Controls/Input'
import PersonalInputs from '../../Shared/Personal/PersonalInputs'
import { Props } from './props.ts'
import { PriceType } from '../../../types/attraction.ts'
import styles from './ReservationPlanAttractionForm.module.css'

const ReservationPlanAttractionForm = ({ attraction, dateFrom, dateTo, formData, handleChange }: Props) => {
    const { t } = useHooks()

    return (
        <div className={styles.form}>
            <PersonalInputs
                inputs={[
                    <Input
                        key={0}
                        type={'date'}
                        name={'dateFrom'}
                        min={dateFrom.toString()}
                        max={dateTo.toString()}
                        placeholder={t('reservation.reservation-plan-date-from')}
                        value={formData.dateFrom}
                        onChange={handleChange}
                        required={true}
                    />,
                    <Input
                        key={1}
                        type={'date'}
                        name={'dateTo'}
                        min={dateFrom.toString()}
                        max={dateTo.toString()}
                        placeholder={t('reservation.reservation-plan-date-to')}
                        value={formData.dateTo}
                        onChange={handleChange}
                        required={true}
                    />
                ]}
            />
            <PersonalInputs
                inputs={[
                    <Input
                        key={2}
                        type={'time'}
                        name={'hourFrom'}
                        placeholder={t('reservation.reservation-plan-hour-from')}
                        value={formData.hourFrom}
                        onChange={handleChange}
                        required={true}
                    />,
                    <Input
                        key={3}
                        type={'time'}
                        name={'hourTo'}
                        placeholder={t('reservation.reservation-plan-hour-to')}
                        value={formData.hourTo}
                        onChange={handleChange}
                        required={true}
                    />
                ]}
            />
            <PersonalInputs
                inputs={[
                    attraction.priceType === PriceType.PERSON && (
                        <Input
                            key={4}
                            type={'number'}
                            name={'people'}
                            placeholder={t('reservation.reservation-plan-people')}
                            value={formData.people}
                            onChange={handleChange}
                            required={false}
                        />
                    ),
                    attraction.priceType === PriceType.ITEM && (
                        <Input
                            key={5}
                            type={'number'}
                            name={'items'}
                            placeholder={t('reservation.reservation-plan-items')}
                            value={formData.items}
                            onChange={handleChange}
                            required={false}
                        />
                    ),
                    <Input
                        key={6}
                        type={'text'}
                        name={'message'}
                        placeholder={t('reservation.reservation-plan-message')}
                        value={formData.message}
                        onChange={handleChange}
                        required={true}
                    />
                ].filter(Boolean)}
            />
        </div>
    )
}

export default ReservationPlanAttractionForm
