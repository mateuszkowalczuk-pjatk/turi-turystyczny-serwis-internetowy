import { useHooks } from '../../../hooks/shared/useHooks.ts'
import Label from '../../Shared/Controls/Label'
import Input from '../../Shared/Controls/Input'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import PersonalPanel from '../../Shared/Personal/PersonalPanel'
import TourismTouristicPlaceLabel from '../../Tourism/TourismTouristicPlaceLabel'
import styles from './ReservationPersonal.module.css'

interface ReservationPersonalProps {
    firstName: string | null
    setFirstName: (value: ((prevState: string | null) => string | null) | string | null) => void
    lastName: string | null
    setLastName: (value: ((prevState: string | null) => string | null) | string | null) => void
    phoneNumber: string | null
    setPhoneNumber: (value: ((prevState: string | null) => string | null) | string | null) => void
    email: string | null
}

const ReservationPersonal = ({
    firstName,
    setFirstName,
    lastName,
    setLastName,
    phoneNumber,
    setPhoneNumber,
    email
}: ReservationPersonalProps) => {
    const { t } = useHooks()

    return (
        <div className={styles.personal}>
            <TextMedium text={'Dane personalne'} />
            <PersonalPanel
                label={<Label text={t('signup-personal.name-surname')} />}
                firstInput={
                    <Input
                        type={'text'}
                        name={'firstName'}
                        placeholder={t('signup-personal.name')}
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        required={true}
                    />
                }
                secondInput={
                    <Input
                        type={'text'}
                        name={'lastName'}
                        placeholder={t('signup-personal.surname')}
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        required={true}
                    />
                }
            />
            <PersonalPanel
                label={<Label text={t('tourism.touristic-place-contact')} />}
                firstInput={<TourismTouristicPlaceLabel text={email || ''} />}
                secondInput={
                    <Input
                        type={'text'}
                        name={'phoneNumber'}
                        placeholder={t('signup-personal.phone-number')}
                        value={phoneNumber}
                        onChange={(e) => setPhoneNumber(e.target.value)}
                        required={true}
                    />
                }
            />
        </div>
    )
}

export default ReservationPersonal
